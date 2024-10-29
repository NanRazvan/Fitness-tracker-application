package com.example.fitness_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/users/**").hasRole("USER")
//                        .requestMatchers("/goals/**", "/workouts/**", "/trainings/**").hasRole("TRAINER")
                        .requestMatchers("/**").hasAnyRole("ADMIN","USER","TRAINER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/start-page", true)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> {
            if ("admin".equals(username)) {
                return User.withUsername(username)
                        .password(passwordEncoder.encode("adminpassword"))
                        .roles("ADMIN")
                        .build();
            } else if (username.startsWith("trainer-")) {
                return User.withUsername(username)
                        .password(passwordEncoder.encode("password"))
                        .roles("TRAINER")
                        .build();
            } else {
                return User.withUsername(username)
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
