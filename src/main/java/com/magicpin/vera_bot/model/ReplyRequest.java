package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReplyRequest {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("trigger_id")
    private String triggerId;

    private String message;

}