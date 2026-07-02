package com.magicpin.vera_bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MetadataController {

    @GetMapping("/metadata")
    public Map<String, Object> metadata() {

        return Map.of(

                "name", "Vera AI Bot",

                "version", "1.0.0",

                "provider", "magicpin",

                "description",
                "AI Merchant Assistant powered by Groq and Spring Boot",

                "capabilities",
                List.of(
                        "merchant_context",
                        "customer_context",
                        "category_context",
                        "trigger_context",
                        "proactive_messaging",
                        "conversation_memory"
                )

        );

    }

}