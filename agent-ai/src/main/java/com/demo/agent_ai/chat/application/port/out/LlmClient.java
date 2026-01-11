package com.demo.agent_ai.chat.application.port.out;

import com.demo.agent_ai.chat.domain.models.ChatMessage;

import java.util.List;

public interface LlmClient {
    String generateResponse(
            List<ChatMessage> historyContext,
            String conversationKnowledgeContext,
            String context
    );
}
