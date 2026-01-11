package com.demo.agent_ai.knowledge.infrastructure.repository;

import com.demo.agent_ai.knowledge.domain.models.KnowledgeChunk;
import com.demo.agent_ai.knowledge.domain.repository.KnowledgeChunkRepository;
import com.demo.agent_ai.knowledge.infrastructure.adapters.SpringDataKnowledgeChunkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class KnowledgeChunkRepositoryImpl implements KnowledgeChunkRepository {

    private final SpringDataKnowledgeChunkRepository springDataKnowledgeChunkRepository;

    @Override
    public KnowledgeChunk save(KnowledgeChunk knowledgeChunk) {
        return springDataKnowledgeChunkRepository.save(knowledgeChunk);
    }

    @Override
    public List<KnowledgeChunk> findAllByConversationId(String conversationId) {
        return springDataKnowledgeChunkRepository.findByConversationId(conversationId, null);
    }

    @Override
    public List<KnowledgeChunk> findTop5ByConversationIdAndContentContaining(String conversationId, String keywords) {
        return List.of();
    }

    @Override
    public boolean deleteByConversationId(String conversationId) {
        springDataKnowledgeChunkRepository.deleteByConversationId(conversationId);
        return true;
    }
}
