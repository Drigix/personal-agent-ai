package com.demo.agent_ai.chat.domain.repository;

import com.demo.agent_ai.chat.domain.models.ChatMessage;

import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage message);
    List<ChatMessage> findByConversationId(String conversationId);
    boolean deleteByConversationId(String conversationId);
}
