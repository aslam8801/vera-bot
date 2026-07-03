package com.magicpin.vera_bot.controller;

import com.magicpin.vera_bot.service.ContextStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MetadataController {

    private final ContextStore contextStore;

    public MetadataController(ContextStore contextStore) {
        this.contextStore = contextStore;
    }

    @GetMapping("/metadata")
    public Map<String, Object> metadata() {

        return Map.of(

                "name", "Vera AI Bot",

                "provider", "magicpin",

                "version", "1.0.0",

                "description",
                "AI Merchant Assistant powered by Groq and Spring Boot",

                "capabilities",
                List.of(
                        "merchant_context",
                        "customer_context",
                        "category_context",
                        "trigger_engine",
                        "conversation_memory",
                        "proactive_messaging"
                ),

                "contexts",
                contextStore.getContextCounts()

        );
    }
}