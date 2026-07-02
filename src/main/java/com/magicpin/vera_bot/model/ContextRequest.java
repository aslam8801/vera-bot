package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ContextRequest {

    private String scope;

    @JsonProperty("context_id")
    private String contextId;

    private Integer version;

    private Map<String, Object> payload;

    @JsonProperty("delivered_at")
    private String deliveredAt;

}