package com.team.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.team.bookstore")
@EnableJpaAuditing
public class BookStoreServerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreServerAppApplication.class, args);
    }

}
