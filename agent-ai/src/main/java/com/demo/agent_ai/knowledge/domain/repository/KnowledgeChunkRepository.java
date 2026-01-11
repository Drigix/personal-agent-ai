package com.demo.agent_ai.knowledge.domain.repository;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeChunk;

import java.util.List;

public interface KnowledgeChunkRepository {
    KnowledgeChunk save(KnowledgeChunk knowledgeDocument);
    List<KnowledgeChunk> findAllByConversationId(String conversationId);
    List<KnowledgeChunk> findTop5ByConversationIdAndContentContaining(String conversationId, String keywords);
    boolean deleteByConversationId(String conversationId);
}
