package com.ritwikrajsingh.java_springboot_rate_limiter.config;

import java.io.IOException;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RateLimitingFilter extends OncePerRequestFilter {

    private RedisTemplate<String, String> template;

    private static final String KEY_PREFIX = "rate-limiter:";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String key = KEY_PREFIX + ip;

        String value = template.opsForValue().get(key);

        if (value == null) {

            template.opsForValue().set(key, "1", Duration.ofMinutes(1));

        } else if (value != null && Integer.parseInt(value) > 5) {

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests!");
            response.getWriter().flush();

            log.info("Too many requests from: " + ip);

            return;

        } else {

            template.opsForValue().increment(key);

        }

        doFilter(request, response, filterChain);

    }

}
