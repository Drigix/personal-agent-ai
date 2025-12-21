package com.demo.agent_ai.shared.infrasctructure.repository;

import com.demo.agent_ai.shared.domain.documents.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String>{
}
