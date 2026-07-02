package com.magicpin.vera_bot.service;

import com.magicpin.vera_bot.conversation.ConversationManager;
import com.magicpin.vera_bot.model.ReplyRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ReplyService {

    private final GroqService groqService;
    private final ContextStore contextStore;
    private final PromptBuilder promptBuilder;
    private final ConversationManager conversationManager;

    public ReplyService(
            GroqService groqService,
            ContextStore contextStore,
            PromptBuilder promptBuilder,
            ConversationManager conversationManager
    ) {
        this.groqService = groqService;
        this.contextStore = contextStore;
        this.promptBuilder = promptBuilder;
        this.conversationManager = conversationManager;
    }

    public String generateReply(ReplyRequest request) {

        String conversationId = request.getConversationId();

        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }

        conversationManager.createConversation(
                conversationId,
                request.getMerchantId(),
                request.getCustomerId(),
                request.getTriggerId()
        );

        conversationManager.addMessage(
                conversationId,
                "merchant",
                request.getMessage()
        );

        Map<String, Object> merchant =
                contextStore.getContext(
                        "merchant",
                        request.getMerchantId()
                );

        Map<String, Object> category = null;

        if (request.getCategoryId() != null) {
            category = contextStore.getContext(
                    "category",
                    request.getCategoryId()
            );
        }

        Map<String, Object> customer = null;

        if (request.getCustomerId() != null) {
            customer = contextStore.getContext(
                    "customer",
                    request.getCustomerId()
            );
        }

        Map<String, Object> trigger = null;

        if (request.getTriggerId() != null) {
            trigger = contextStore.getContext(
                    "trigger",
                    request.getTriggerId()
            );
        }

        String prompt = promptBuilder.buildPrompt(
                conversationId,
                merchant,
                category,
                customer,
                trigger,
                request.getMessage()
        );

        String reply = groqService.askGroq(prompt);

        conversationManager.addMessage(
                conversationId,
                "assistant",
                reply
        );

        return reply;
    }
}