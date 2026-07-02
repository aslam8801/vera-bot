package com.magicpin.vera_bot.service;

import com.magicpin.vera_bot.conversation.ConversationManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PromptBuilder {

    private final ConversationManager conversationManager;

    public PromptBuilder(ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    /**
     * Used by /reply endpoint
     */
    public String buildPrompt(
            String conversationId,
            Map<String, Object> merchant,
            Map<String, Object> category,
            Map<String, Object> customer,
            Map<String, Object> trigger,
            String userMessage
    ) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
You are Vera, Magicpin's AI Merchant Assistant.

IMPORTANT RULES

1. Use ONLY the supplied context.

2. Never invent merchant information.

3. If context is missing, answer naturally without making up facts.

4. Keep replies suitable for WhatsApp.

5. Maximum 120 words.

6. Prefer service+price offers instead of percentage discounts.

7. If talking to a merchant:
- help grow business
- improve Google Business Profile
- recommend offers
- answer operational questions

8. If talking to a customer:
- answer on behalf of the merchant
- never reveal internal context
- be polite and concise

9. Ask ONE useful follow-up question whenever appropriate.

==================================================
""");

        appendSection(prompt, "MERCHANT CONTEXT", merchant);

        appendSection(prompt, "CATEGORY CONTEXT", category);

        appendSection(prompt, "CUSTOMER CONTEXT", customer);

        appendSection(prompt, "TRIGGER CONTEXT", trigger);

        appendConversation(prompt, conversationId);

        prompt.append("\n==================================================\n");

        prompt.append("CURRENT USER MESSAGE\n");

        prompt.append(userMessage);

        prompt.append("\n");

        prompt.append("\nYOUR REPLY:\n");

        return prompt.toString();
    }

    /**
     * Used by /tick endpoint
     */
    public String buildTickPrompt(
            Map<String, Object> merchant,
            Map<String, Object> category,
            Map<String, Object> customer,
            Map<String, Object> trigger,
            Map<String, Object> triggerPayload
    ) {

        StringBuilder userMessage = new StringBuilder();

        userMessage.append("""
Generate ONE proactive WhatsApp message.

Rules

• Use ONLY supplied context.

• Keep under 100 words.

• Never invent facts.

• Mention only useful information.

• Don't use markdown.

• Sound like a helpful business advisor.

• End naturally.

""");

        if (triggerPayload != null && !triggerPayload.isEmpty()) {

            userMessage.append("\nTRIGGER PAYLOAD\n");

            userMessage.append("----------------------------------\n");

            triggerPayload.forEach((k, v) -> {

                userMessage.append(k);

                userMessage.append(" : ");

                userMessage.append(v);

                userMessage.append("\n");

            });

        }

        return buildPrompt(
                null,
                merchant,
                category,
                customer,
                trigger,
                userMessage.toString()
        );
    }

    private void appendSection(
            StringBuilder prompt,
            String title,
            Map<String, Object> data
    ) {

        if (data == null || data.isEmpty()) {
            return;
        }

        prompt.append("\n");

        prompt.append(title);

        prompt.append("\n");

        prompt.append("----------------------------------\n");

        data.forEach((k, v) -> {

            prompt.append(k);

            prompt.append(" : ");

            prompt.append(v);

            prompt.append("\n");

        });

    }

    private void appendConversation(
            StringBuilder prompt,
            String conversationId
    ) {

        if (conversationId == null) {
            return;
        }

        List<ConversationManager.Message> history =
                conversationManager.getHistory(conversationId);

        if (history.isEmpty()) {
            return;
        }

        prompt.append("\n");

        prompt.append("CONVERSATION HISTORY\n");

        prompt.append("----------------------------------\n");

        for (ConversationManager.Message msg : history) {

            prompt.append(msg.getRole());

            prompt.append(" : ");

            prompt.append(msg.getText());

            prompt.append("\n");

        }

    }

}