package edu.cit.hapongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests -> 
                    authorizeRequests
                        .anyRequest().permitAll()) // Allow all requests without authentication
                .formLogin().disable()  // Disable the default login page
                .oauth2Login().disable() // Disable OAuth2 login redirection
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection if not needed
                .build();
    }
}
