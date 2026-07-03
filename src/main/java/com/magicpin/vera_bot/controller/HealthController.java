package com.magicpin.vera_bot.controller;

import com.magicpin.vera_bot.service.ContextStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class HealthController {

    private final ContextStore contextStore;
    public HealthController(ContextStore contextStore) {
        this.contextStore = contextStore;
    }

    @GetMapping("/healthz")
    public Map<String, Object> healthz() {

        return Map.of(
                "status", "ok",
                "service", "vera-bot",
                "version", "1.0.0",
                "timestamp", Instant.now().toString(),
                "contexts", contextStore.getContextCounts()
        );
    }

}