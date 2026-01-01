package com.demo.agent_ai.chat.infrasctructure.adapters;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByConversationId(String conversationId);

    void deleteByConversationId(String conversationId);
}
