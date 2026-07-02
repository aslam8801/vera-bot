package com.magicpin.vera_bot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyResponse {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("customer_id")
    private String customerId;

    private String reply;

    private String cta;

}