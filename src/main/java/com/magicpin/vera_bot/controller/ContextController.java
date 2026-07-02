package com.magicpin.vera_bot.controller;

import com.magicpin.vera_bot.model.ContextRequest;
import com.magicpin.vera_bot.service.ContextStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class ContextController {

    private final ContextStore contextStore;

    public ContextController(ContextStore contextStore) {
        this.contextStore = contextStore;
    }

    @PostMapping("/context")
    public ResponseEntity<Map<String, Object>> uploadContext(
            @RequestBody ContextRequest request) {

        boolean accepted = contextStore.saveContext(
                request.getScope(),
                request.getContextId(),
                request.getVersion(),
                request.getPayload()
        );

        return ResponseEntity.ok(
                Map.of(
                        "accepted", accepted,
                        "stored_at", Instant.now().toString()
                )
        );
    }
}