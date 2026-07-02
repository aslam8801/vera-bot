package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TickRequest {

    /**
     * Current simulated timestamp
     */
    private String now;

    /**
     * Trigger IDs active during this tick
     */
    @JsonProperty("available_triggers")
    private List<String> availableTriggers;

}