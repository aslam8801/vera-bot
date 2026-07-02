package com.magicpin.vera_bot.service;

import com.magicpin.vera_bot.model.Action;
import com.magicpin.vera_bot.model.TickRequest;
import com.magicpin.vera_bot.model.TickResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TickService {

    private final ContextStore contextStore;
    private final PromptBuilder promptBuilder;
    private final GroqService groqService;

    public TickService(
            ContextStore contextStore,
            PromptBuilder promptBuilder,
            GroqService groqService
    ) {
        this.contextStore = contextStore;
        this.promptBuilder = promptBuilder;
        this.groqService = groqService;
    }

    public TickResponse processTick(TickRequest request) {

        TickResponse response = new TickResponse();

        if (request == null
                || request.getAvailableTriggers() == null
                || request.getAvailableTriggers().isEmpty()) {
            return response;
        }

        List<Action> actions = new ArrayList<>();

        for (String triggerId : request.getAvailableTriggers()) {

            Map<String, Object> trigger =
                    contextStore.getContext("trigger", triggerId);

            if (trigger == null) {
                continue;
            }

            Action action = buildAction(triggerId, trigger);

            if (action != null) {
                actions.add(action);
            }
        }

        response.setActions(actions);

        return response;
    }

    /**
     * Build one Action from one TriggerContext.
     * (Implemented in 8B-2)
     */
    @SuppressWarnings("unchecked")
    private Action buildAction(
            String triggerId,
            Map<String, Object> trigger
    ) {

        String merchantId = (String) trigger.get("merchant_id");
        String customerId = (String) trigger.get("customer_id");
        String kind = (String) trigger.get("kind");
        String suppressionKey = (String) trigger.get("suppression_key");

        Map<String, Object> triggerPayload =
                (Map<String, Object>) trigger.get("payload");

        if (merchantId == null) {
            return null;
        }

        Map<String, Object> merchant =
                contextStore.getContext(
                        "merchant",
                        merchantId
                );

        if (merchant == null) {
            return null;
        }

        String categorySlug = (String) merchant.get("category_slug");

        Map<String, Object> category =
                contextStore.getContext(
                        "category",
                        categorySlug
                );

        Map<String, Object> customer = null;

        if (customerId != null) {

            customer = contextStore.getContext(
                    "customer",
                    customerId
            );

        }

        String prompt = promptBuilder.buildTickPrompt(
                merchant,
                category,
                customer,
                trigger,
                triggerPayload
        );

        String reply = groqService.askGroq(prompt);

        Action action = new Action();

        action.setConversationId(UUID.randomUUID().toString());

        action.setMerchantId(merchantId);

        action.setCustomerId(customerId);

        action.setTriggerId(triggerId);

        action.setSendAs("vera");

        action.setTemplateName(kind);

        action.setBody(reply);

        action.setCta("reply");

        action.setSuppressionKey(suppressionKey);

        action.setRationale(
                "Generated from trigger: " + kind
        );

        return action;

    }



}