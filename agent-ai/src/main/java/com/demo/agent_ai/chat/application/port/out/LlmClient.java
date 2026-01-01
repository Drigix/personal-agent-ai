package com.demo.agent_ai.chat.application.port.out;

public interface LlmClient {
    String generateResponse(String context);
}
