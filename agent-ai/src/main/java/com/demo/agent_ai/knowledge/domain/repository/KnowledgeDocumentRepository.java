package com.demo.agent_ai.knowledge.domain.repository;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeDocument;

import java.util.List;

public interface KnowledgeDocumentRepository {
    KnowledgeDocument save(KnowledgeDocument knowledgeDocument);
    List<KnowledgeDocument> findAllByConversationId(String conversationId);
    Boolean isContentHashExistInConversation(String conversationId, String contentHash);
    boolean deleteByConversationId(String conversationId);
}
