package com.demo.agent_ai.knowledge.infrastructure.repository;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeDocument;
import com.demo.agent_ai.knowledge.domain.repository.KnowledgeDocumentRepository;
import com.demo.agent_ai.knowledge.infrastructure.adapters.SpringDataKnowledgeDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class KnowledgeDocumentRepositoryImpl implements KnowledgeDocumentRepository {

    private final SpringDataKnowledgeDocumentRepository springDataKnowledgeDocumentRepository;

    @Override
    public KnowledgeDocument save(KnowledgeDocument knowledgeDocument) {
        return springDataKnowledgeDocumentRepository.save(knowledgeDocument);
    }

    @Override
    public List<KnowledgeDocument> findAllByConversationId(String conversationId) {
        return List.of();
    }

    @Override
    public Boolean isContentHashExistInConversation(String conversationId, String contentHash) {
        KnowledgeDocument knowledgeDocument = springDataKnowledgeDocumentRepository.findOneByConversationIdAndContentHash(conversationId, contentHash);
        return knowledgeDocument != null && knowledgeDocument.getId() != null;
    }

    @Override
    public boolean deleteByConversationId(String conversationId) {
        springDataKnowledgeDocumentRepository.deleteByConversationId(conversationId);
        return true;
    }
}
