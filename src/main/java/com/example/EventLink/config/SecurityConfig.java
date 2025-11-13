package com.example.EventLink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults()) // Enable CORS for frontend integration
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API usage
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/api/auth/**").permitAll(); // Allow auth endpoints
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("/api/auth/success", true)
                    .failureUrl("/api/auth/failure"))
                .logout(logout -> logout
                    .logoutUrl("/api/auth/logout")
                    .logoutSuccessUrl("/api/auth/logout-success"))
                .build();
    }
}
