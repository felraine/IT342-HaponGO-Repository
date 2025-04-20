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
                .cors() // ✅ Enable CORS here
                .and()
                .authorizeHttpRequests(authorizeRequests -> 
                    authorizeRequests.anyRequest().permitAll())
                .formLogin().disable()
                .oauth2Login().disable()
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
