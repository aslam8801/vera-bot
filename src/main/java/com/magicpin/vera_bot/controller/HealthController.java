package com.magicpin.vera_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class HealthController {

    @GetMapping("/healthz")
    public Map<String, Object> healthz() {

        return Map.of(
                "status", "ok",
                "service", "vera-bot",
                "timestamp", Instant.now().toString()
        );

    }

}