package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("send_as")
    private String sendAs;

    @JsonProperty("trigger_id")
    private String triggerId;

    @JsonProperty("template_name")
    private String templateName;

    private String body;

    private String cta;

    @JsonProperty("suppression_key")
    private String suppressionKey;

    private String rationale;

}