package com.team.bookstore.Configs;
import com.team.bookstore.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    JWTHS256Decoder jwths256Decoder;
    @Autowired
    PasswordEncoder passwordEncoder;
    public static String[] PublicEndpoints = {"auth/**","author/all","author" +
            "/find","book/all","book/find","category/all","category/find",
            "customer/register","feedback/all","gallery/all","gallery/find",
            "keyword/all","keyword/find","language/all","language/find",
            "provider/all","provider/find","publisher/all","publisher/all",
            "ebook/all"};
    public static String[] CustomerEndpoints = {"/book/mine","/customer" +
            "/myinfo","customer/my-payments","customer/my-orders","customer" +
            "/update/**","/customer/create/info/**","/feedback/add",
            "/feedback/all","/order/add","/order/cancel/**"};
    public static String[] StaffEndpoints = {"/author/**",
            "/language/**","/publisher/**","/provider/**","/staff/**","/order" +
            "/all","/payment/all","/feedback/all",
            "/keyword/**","/order/update-status/{id}","/import/**"};
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PublicEndpoints).permitAll()
                        .requestMatchers(CustomerEndpoints).hasAnyRole(Role.CUSTOMER.name(),Role.ADMIN.name())
                        .requestMatchers(StaffEndpoints).hasAnyRole(Role.STAFF.name(),Role.ADMIN.name())
                        .requestMatchers("/delete","/user").hasRole(Role.ADMIN.name())
                        .requestMatchers("/message/loadchat").authenticated()
                        .anyRequest().permitAll()
                )
                //.sessionManagement(sess -> sess.sessionCreationPolicy
                // (SessionCreationPolicy.ALWAYS)
                        //.invalidSessionUrl("/v1/login?expired")
                        //.maximumSessions(1)
                        //.expiredUrl("/v1/login?expired"))
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.logoutUrl("/v1/logout").invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID"))
                .headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable));

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwths256Decoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();

    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}