package com.team.bookstore.Configs;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Lam",
                        email = "22520018@gm.uit.edu.vn"
                ),
                description = "OpenAPI document for Bookstore",
                title = "OpenAPI for BookStore",
                version = "1.0.0",
                license = @License(
                        name = "License name",
                        url = "null"
                ),
                termsOfService = "Terms of service"
        ),
        servers = @Server(
                description = "Local SV",
                url = "http://localhost:8030/BookStore"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
