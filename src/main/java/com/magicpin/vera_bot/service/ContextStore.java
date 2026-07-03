package com.magicpin.vera_bot.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ContextStore {

    /**
     * One stored context object
     */
    private static class ContextEntry {

        private final int version;
        private final Map<String, Object> payload;

        ContextEntry(int version, Map<String, Object> payload) {
            this.version = version;
            this.payload = payload;
        }

        public int getVersion() {
            return version;
        }

        public Map<String, Object> getPayload() {
            return payload;
        }
    }

    /**
     * scope -> contextId -> ContextEntry
     */
    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, ContextEntry>> store =
            new ConcurrentHashMap<>();

    /**
     * Save context
     *
     * Returns:
     * true  -> accepted
     * false -> stale version
     */
    public synchronized boolean saveContext(
            String scope,
            String contextId,
            Integer version,
            Map<String, Object> payload
    ) {

        if (scope == null || contextId == null || payload == null) {
            return false;
        }

        int newVersion = version == null ? 1 : version;

        store.putIfAbsent(scope, new ConcurrentHashMap<>());

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        ContextEntry existing = scopedStore.get(contextId);

        /*
         * Judge requirement:
         * Same version = ignore
         * Older version = reject
         * Higher version = replace
         */

        if (existing != null && existing.getVersion() >= newVersion) {
            return false;
        }

        scopedStore.put(
                contextId,
                new ContextEntry(newVersion, payload)
        );

        return true;
    }

    /**
     * Fetch one context
     */
    public Map<String, Object> getContext(
            String scope,
            String contextId
    ) {

        if (scope == null || contextId == null) {
            return null;
        }

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        if (scopedStore == null) {
            return null;
        }

        ContextEntry entry = scopedStore.get(contextId);

        if (entry == null) {
            return null;
        }

        return entry.getPayload();
    }

    /**
     * Latest version
     */
    public Integer getVersion(
            String scope,
            String contextId
    ) {

        if (scope == null || contextId == null) {
            return null;
        }

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        if (scopedStore == null) {
            return null;
        }

        ContextEntry entry = scopedStore.get(contextId);

        if (entry == null) {
            return null;
        }

        return entry.getVersion();
    }

    /**
     * Number of contexts in one scope
     */
    public int getScopeCount(String scope) {

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        return scopedStore == null ? 0 : scopedStore.size();
    }

    /**
     * Health endpoint helper
     */
    public Map<String, Integer> getContextCounts() {

        return Map.of(
                "merchant", getScopeCount("merchant"),
                "category", getScopeCount("category"),
                "customer", getScopeCount("customer"),
                "trigger", getScopeCount("trigger")
        );
    }

    /**
     * Return all ids inside one scope
     */
    public Set<String> getAllContextIds(String scope) {

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        if (scopedStore == null) {
            return Set.of();
        }

        return scopedStore.keySet();
    }

    /**
     * Return every context inside one scope.
     *
     * Used by TickService.
     */
    public Map<String, ContextEntry> getAllContexts(String scope) {

        ConcurrentHashMap<String, ContextEntry> scopedStore =
                store.get(scope);

        if (scopedStore == null) {
            return Map.of();
        }

        return Map.copyOf(scopedStore);
    }

    /**
     * Clear memory.
     * Useful during local testing.
     */
    public void clearAll() {
        store.clear();
    }
}