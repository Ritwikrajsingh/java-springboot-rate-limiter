package com.ritwikrajsingh.java_springboot_rate_limiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RateLimiterController {

    @GetMapping
    public String hello() {

        return "Working fine!";

    }

}
