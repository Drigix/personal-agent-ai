package com.demo.agent_ai.chat.infrasctructure.adapters;

import com.demo.agent_ai.chat.domain.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataConversationRepository extends MongoRepository<Conversation, String>{
}
