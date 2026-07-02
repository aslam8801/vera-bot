# Vera AI Bot

AI-powered Spring Boot backend built for the **Magicpin Vera AI Challenge**.

The application provides:

- Merchant Context Management
- Customer Context Management
- Category Context Management
- Trigger Context Management
- AI Reply Generation (Groq LLM)
- Proactive Trigger-based Messaging
- Conversation Memory
- Health & Metadata APIs

---

# Tech Stack

- Java 21
- Spring Boot 3
- Groq LLM
- Maven
- Docker
- Render Deployment

---

# Live API

Base URL

```
https://vera-bot-b0um.onrender.com
```

---

# API Endpoints

## 1. Health Check

GET

```
/v1/healthz
```

Response

```json
{
  "status": "ok",
  "service": "vera-bot",
  "timestamp": "2026-07-02T23:47:57Z"
}
```

---

## 2. Metadata

GET

```
/v1/metadata
```

Response

```json
{
  "name": "Vera AI Bot",
  "description": "AI Merchant Assistant powered by Groq and Spring Boot",
  "capabilities": [
    "merchant_context",
    "customer_context",
    "category_context",
    "trigger_context",
    "proactive_messaging",
    "conversation_memory"
  ],
  "provider": "magicpin",
  "version": "1.0.0"
}
```

---

# Upload Context

All contexts are uploaded using the same endpoint.

POST

```
/v1/context
```

---

## 3. Upload Merchant

Request

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

## 4. Upload Category

Request

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

## 5. Upload Customer

Request

```json
{
  "scope": "customer",
  "context_id": "customer_001",
  "version": 1,
  "payload": {
    "customer_id": "customer_001",
    "name": "Rahul",
    "visit_count": 0,
    "last_visit": null
  }
}
```

---

## 6. Upload Trigger

Request

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

---

Typical Response

```json
{
  "accepted": true,
  "stored_at": "2026-07-02T23:45:28Z"
}
```

---

# AI Reply

POST

```
/v1/reply
```

Request

```json
{
  "conversation_id": "conv_1",
  "merchant_id": "merchant_001",
  "category_id": "dentists",
  "customer_id": "customer_001",
  "message": "Suggest an offer for new customers."
}
```

Sample Response

```json
{
  "conversation_id": "conv_1",
  "merchant_id": "merchant_001",
  "customer_id": "customer_001",
  "reply": "Offer a Welcome Discount of 20% for first-time customers.",
  "cta": "reply"
}
```

---

# Tick Endpoint

POST

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
      "body": "Hi, I'm Vera from Magicpin. I can help ABC Dental Clinic attract more customers by promoting your services.",
      "cta": "reply",
      "suppression_key": "abc123",
      "rationale": "Generated from trigger: research"
    }
  ]
}
```

---

# Running Locally

Clone repository

```bash
git clone https://github.com/aslam8801/vera-bot.git
```

Go to project

```bash
cd vera-bot
```

Set environment variable

```text
GROQ_API_KEY=YOUR_GROQ_API_KEY
```

Run

```bash
./mvnw spring-boot:run
```

Application runs on

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
docker run -p 8080:8080 \
-e GROQ_API_KEY=YOUR_GROQ_API_KEY \
vera-bot
```

---

# Repository

https://github.com/aslam8801/vera-bot

---

# Live Deployment

https://vera-bot-b0um.onrender.com

---

Built for the **Magicpin Vera AI Challenge** using Spring Boot, Groq LLM, and Docker.