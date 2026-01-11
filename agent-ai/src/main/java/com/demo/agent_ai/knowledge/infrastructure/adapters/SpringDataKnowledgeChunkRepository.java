package com.demo.agent_ai.knowledge.infrastructure.adapters;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataKnowledgeChunkRepository extends MongoRepository<KnowledgeChunk, String> {
    List<KnowledgeChunk> findByConversationId(String conversationId, Pageable pageable);
    void deleteByConversationId(String conversationId);
}
