package com.team.bookstore.Configs;

import com.team.bookstore.Entities.Role;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Entities.User_Role;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Repositories.UserRepository;
import com.team.bookstore.Repositories.User_RoleRepository;
import com.team.bookstore.Services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Log4j2
public class ApplicationInitConfig {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    User_RoleRepository user_roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        User_RoleRepository user_roleRepository){
        return args -> {
            if(!userRepository.existsByUsername("admin"))
            {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("helloadmin"));
                Set<User_Role> user_roles = new HashSet<>();
                log.info("HERE");
                List<Role>     allRoles   = roleRepository.findAll();
                allRoles.forEach(role -> {
                    User_Role user_role = new User_Role();
                    user_role.setUser(user);
                    user_role.setRole(role);
                    user_roles.add(user_role);
                });
                userRepository.save(user);
                user_roleRepository.saveAll(user_roles);
            }
        };
    }
}
