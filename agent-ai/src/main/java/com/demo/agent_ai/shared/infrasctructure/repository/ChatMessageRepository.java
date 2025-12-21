package com.demo.agent_ai.shared.infrasctructure.repository;

import com.demo.agent_ai.shared.domain.documents.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
