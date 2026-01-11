package com.demo.agent_ai.knowledge.infrastructure.adapters;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataKnowledgeDocumentRepository extends MongoRepository<KnowledgeDocument, String> {
    KnowledgeDocument findOneByConversationIdAndContentHash(String conversationId, String contentHash);
    void deleteByConversationId(String conversationId);
}
