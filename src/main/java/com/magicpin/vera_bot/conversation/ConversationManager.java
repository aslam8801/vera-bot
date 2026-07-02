package com.magicpin.vera_bot.conversation;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConversationManager {

    /**
     * One conversation object
     */
    public static class Conversation {

        private final String conversationId;

        private String merchantId;

        private String customerId;

        private String triggerId;

        private Instant lastActivity;

        private final List<Message> history = new ArrayList<>();

        public Conversation(String conversationId) {
            this.conversationId = conversationId;
            this.lastActivity = Instant.now();
        }

        public String getConversationId() {
            return conversationId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getTriggerId() {
            return triggerId;
        }

        public void setTriggerId(String triggerId) {
            this.triggerId = triggerId;
        }

        public Instant getLastActivity() {
            return lastActivity;
        }

        public void setLastActivity(Instant lastActivity) {
            this.lastActivity = lastActivity;
        }

        public List<Message> getHistory() {
            return history;
        }
    }

    /**
     * One message inside conversation
     */
    public static class Message {

        private final String role;

        private final String text;

        private final Instant timestamp;

        public Message(String role, String text) {
            this.role = role;
            this.text = text;
            this.timestamp = Instant.now();
        }

        public String getRole() {
            return role;
        }

        public String getText() {
            return text;
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }

    /**
     * All active conversations
     */
    private final ConcurrentHashMap<String, Conversation> conversations =
            new ConcurrentHashMap<>();

    /**
     * Create conversation if absent
     */
    public Conversation createConversation(
            String conversationId,
            String merchantId,
            String customerId,
            String triggerId
    ) {

        return conversations.computeIfAbsent(conversationId, id -> {

            Conversation conversation = new Conversation(id);

            conversation.setMerchantId(merchantId);

            conversation.setCustomerId(customerId);

            conversation.setTriggerId(triggerId);

            return conversation;
        });
    }

    /**
     * Get conversation
     */
    public Conversation getConversation(String conversationId) {
        return conversations.get(conversationId);
    }

    /**
     * Add one message
     */
    public void addMessage(
            String conversationId,
            String role,
            String text
    ) {

        Conversation conversation = conversations.get(conversationId);

        if (conversation == null)
            return;

        conversation.getHistory().add(
                new Message(role, text)
        );

        conversation.setLastActivity(Instant.now());
    }

    /**
     * Entire history
     */
    public List<Message> getHistory(String conversationId) {

        Conversation conversation = conversations.get(conversationId);

        if (conversation == null)
            return List.of();

        return conversation.getHistory();
    }

    /**
     * Number of conversations
     */
    public int totalConversations() {
        return conversations.size();
    }

    /**
     * Delete one conversation
     */
    public void removeConversation(String conversationId) {
        conversations.remove(conversationId);
    }

    /**
     * Clear all conversations
     */
    public void clear() {
        conversations.clear();
    }

}