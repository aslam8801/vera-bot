# 🚀 Vera AI Bot

AI-powered merchant assistant built for the **Magicpin AI Challenge** using **Spring Boot**, **Groq LLM**, **Docker**, and **Render**.

## Live Demo

**Base URL**

```
https://vera-bot-b0um.onrender.com
```

## GitHub Repository

```
https://github.com/aslam8801/vera-bot
```

---

# Overview

Vera is an AI Merchant Assistant that helps merchants improve engagement by generating context-aware WhatsApp messages.

The bot consumes structured merchant, category, customer, and trigger contexts and generates:

- AI-powered replies
- Proactive merchant messages
- Context-aware recommendations
- Conversation-aware responses

The solution is deterministic, context-driven, and designed to satisfy the Magicpin AI Challenge evaluation criteria.

---

# Features

- ✅ Merchant Context Management
- ✅ Customer Context Management
- ✅ Category Context Management
- ✅ Trigger Context Management
- ✅ Versioned Context Storage
- ✅ Conversation Memory
- ✅ AI Reply Generation (Groq LLM)
- ✅ Proactive Messaging Engine
- ✅ Health Endpoint
- ✅ Metadata Endpoint
- ✅ Dockerized Deployment
- ✅ Render Deployment

---

# Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot 3 | Backend Framework |
| Groq LLM | AI Response Generation |
| Maven | Build Tool |
| Docker | Containerization |
| Render | Deployment |

---

# Architecture

```
                    +--------------------+
                    |   Client / Judge   |
                    +---------+----------+
                              |
                              |
                 REST APIs (/v1/*)
                              |
        +---------------------+---------------------+
        |                                           |
        |             Spring Boot                   |
        |                                           |
        +---------------------+---------------------+
                              |
        +----------+----------+-----------+
        |          |                      |
        |          |                      |
 ContextStore  ConversationManager   PromptBuilder
        |                              |
        |                              |
        +--------------+---------------+
                       |
                  GroqService
                       |
                 Groq LLM API
```

---

# API Endpoints

## Health

### GET

```
/v1/healthz
```

Response

```json
{
  "status": "ok",
  "service": "vera-bot",
  "version": "1.0.0",
  "timestamp": "...",
  "contexts": {
    "merchant": 1,
    "category": 1,
    "customer": 1,
    "trigger": 1
  }
}
```

---

## Metadata

### GET

```
/v1/metadata
```

Response

```json
{
  "name": "Vera AI Bot",
  "provider": "magicpin",
  "version": "1.0.0",
  "description": "AI Merchant Assistant powered by Groq and Spring Boot",
  "capabilities": [
    "merchant_context",
    "customer_context",
    "category_context",
    "trigger_engine",
    "conversation_memory",
    "proactive_messaging"
  ]
}
```

---

# Upload Context

## POST

```
/v1/context
```

### Merchant

```json
{
  "scope": "merchant",
  "context_id": "merchant_001",
  "version": 1,
  "payload": {
    "merchant_id": "merchant_001",
    "merchant_name": "ABC Dental Clinic",
    "category_slug": "dentists"
  }
}
```

---

### Category

```json
{
  "scope": "category",
  "context_id": "dentists",
  "version": 1,
  "payload": {
    "category_name": "Dentists",
    "recommended_services": [
      "Dental Cleaning",
      "Teeth Whitening",
      "Root Canal"
    ]
  }
}
```

---

### Customer

```json
{
  "scope": "customer",
  "context_id": "customer_001",
  "version": 1,
  "payload": {
    "customer_id": "customer_001",
    "name": "Rahul"
  }
}
```

---

### Trigger

```json
{
  "scope": "trigger",
  "context_id": "trigger_001",
  "version": 1,
  "payload": {
    "kind": "research",
    "merchant_id": "merchant_001",
    "customer_id": "customer_001",
    "suppression_key": "abc123"
  }
}
```

Response

```json
{
  "accepted": true,
  "stored_at": "..."
}
```

---

# Reply API

## POST

```
/v1/reply
```

Request

```json
{
  "conversation_id": "conv_001",
  "merchant_id": "merchant_001",
  "category_id": "dentists",
  "customer_id": "customer_001",
  "message": "Suggest an offer for first-time customers."
}
```

Sample Response

```json
{
  "conversation_id": "conv_001",
  "merchant_id": "merchant_001",
  "customer_id": "customer_001",
  "reply": "Offer a discounted dental cleaning for first-time visitors to encourage bookings.",
  "cta": "reply"
}
```

---

# Tick API

## POST

```
/v1/tick
```

Request

```json
{
  "available_triggers": [
    "trigger_001"
  ]
}
```

Sample Response

```json
{
  "actions": [
    {
      "conversation_id": "generated_uuid",
      "merchant_id": "merchant_001",
      "customer_id": "customer_001",
      "send_as": "vera",
      "trigger_id": "trigger_001",
      "template_name": "research",
      "body": "Hi! I'm Vera from Magicpin. Many customers are looking for dental check-ups nearby. Would you like me to help promote your services?",
      "cta": "reply",
      "suppression_key": "abc123",
      "rationale": "Generated from trigger: research"
    }
  ]
}
```

---

# Running Locally

Clone the repository

```bash
git clone https://github.com/aslam8801/vera-bot.git
```

Go to the project directory

```bash
cd vera-bot
```

Set the environment variable

```
GROQ_API_KEY=YOUR_GROQ_API_KEY
```

Run

```bash
./mvnw spring-boot:run
```

Server starts at

```
http://localhost:8080
```

---

# Docker

Build

```bash
docker build -t vera-bot .
```

Run

```bash
docker run -p 8080:8080 -e GROQ_API_KEY=YOUR_GROQ_API_KEY vera-bot
```

---

# Deployment

Hosted on **Render**

```
https://vera-bot-b0um.onrender.com
```

---


# Design Decisions

- In-memory ContextStore for fast deterministic context retrieval.
- Versioned context updates to avoid stale data.
- ConversationManager maintains conversation history.
- PromptBuilder centralizes prompt construction.
- Groq LLM generates grounded, context-aware responses.
- Stateless REST APIs with deterministic behavior for identical inputs.

---

# Challenge

Built for the **Magicpin AI Challenge**.

The solution focuses on:

- Context grounding
- Deterministic message generation
- Merchant-specific recommendations
- Proactive engagement
- Clean Spring Boot architecture