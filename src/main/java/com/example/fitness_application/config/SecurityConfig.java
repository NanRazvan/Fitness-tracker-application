package com.example.fitness_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll() // Allow all requests without authentication
                )
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .formLogin(AbstractHttpConfigurer::disable); // Disable form login

        return http.build();
    }
}
