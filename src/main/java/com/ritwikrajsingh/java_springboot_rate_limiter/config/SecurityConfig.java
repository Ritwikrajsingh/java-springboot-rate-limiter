package com.ritwikrajsingh.java_springboot_rate_limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, RateLimitingFilter rateLimitingFilter)
            throws Exception {

        return security
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public RateLimitingFilter rateLimitingFilter(RedisTemplate<String, String> template) {

        return new RateLimitingFilter(template);

    }

}
