package com.magicpin.vera_bot.controller;

import com.magicpin.vera_bot.model.TickRequest;
import com.magicpin.vera_bot.model.TickResponse;
import com.magicpin.vera_bot.service.TickService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TickController {

    private final TickService tickService;

    public TickController(TickService tickService) {
        this.tickService = tickService;
    }

    @PostMapping("/tick")
    public TickResponse tick(
            @RequestBody TickRequest request
    ) {

        return tickService.processTick(request);

    }

}