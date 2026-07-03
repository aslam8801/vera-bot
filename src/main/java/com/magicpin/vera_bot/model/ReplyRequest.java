package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReplyRequest {

    @JsonProperty("conversation_id")
    @JsonAlias("conversationId")
    private String conversationId;

    @JsonProperty("merchant_id")
    @JsonAlias("merchantId")
    private String merchantId;

    @JsonProperty("category_id")
    @JsonAlias("categoryId")
    private String categoryId;

    @JsonProperty("customer_id")
    @JsonAlias("customerId")
    private String customerId;

    @JsonProperty("trigger_id")
    @JsonAlias("triggerId")
    private String triggerId;

    @JsonProperty("message")
    private String message;
}