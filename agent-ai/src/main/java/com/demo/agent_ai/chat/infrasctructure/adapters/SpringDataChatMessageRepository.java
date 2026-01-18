package com.demo.agent_ai.chat.infrasctructure.adapters;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByConversationId(String conversationId);

    List<ChatMessage> findByConversationId(String conversationId, Pageable pageable);

    void deleteByConversationId(String conversationId);
}
