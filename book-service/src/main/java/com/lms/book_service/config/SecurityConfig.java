package com.lms.book_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/", "/logout").permitAll()
                .requestMatchers("/api/**").permitAll() // Allow API access
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow static resources
                .anyRequest().permitAll() // Temporarily allow all for custom auth
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .formLogin(form -> form.disable()) // Disable Spring Security's form login
            .httpBasic(basic -> basic.disable()) // Disable basic auth
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.deny()));
        
        return http.build();
    }
}
