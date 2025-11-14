package com.example.EventLink.config;

import com.example.EventLink.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Comma-separated list; override in env with APP_CORS_ALLOWED_ORIGINS
    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:19006}")
    private String allowedOrigins;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  return http
      .csrf(csrf -> csrf.disable())
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers(
            "/",
            "/test-db",
            "/actuator/health"
        ).permitAll();
        auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        auth.anyRequest().authenticated();
      })
      // 👇 return 401 for APIs instead of 302 redirect
      //Commented out to test routes with oauth login
      //.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
      .oauth2Login(oauth -> {}) // withDefaults()
      .build();
}

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        cfg.setAllowedOrigins(origins); // or cfg.setAllowedOriginPatterns(origins) if you need wildcards
        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}

