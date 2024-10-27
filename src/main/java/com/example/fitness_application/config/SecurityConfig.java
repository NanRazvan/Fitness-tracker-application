package com.example.fitness_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/trainings/**").hasRole("TRAINER")
                        .requestMatchers("/goals/**", "/workouts/**", "/users/**").hasAnyRole("USER", "TRAINER")
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/start-page", true) // asigură-te că există această pagină
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("Razvan")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails trainer = User.withUsername("trainer")
//                .password(passwordEncoder.encode("password"))
//                .roles("TRAINER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, trainer);
//    }

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
