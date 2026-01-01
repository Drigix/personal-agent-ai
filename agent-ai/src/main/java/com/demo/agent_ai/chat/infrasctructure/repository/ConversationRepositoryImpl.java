package com.demo.agent_ai.chat.infrasctructure.repository;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.chat.domain.repository.ConversationRepository;
import com.demo.agent_ai.chat.infrasctructure.adapters.SpringDataConversationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ConversationRepositoryImpl implements ConversationRepository {

    private final SpringDataConversationRepository springDataConversationRepository;

    @Override
    public Conversation save(Conversation conversation) {
        return springDataConversationRepository.save(conversation);
    }

    @Override
    public List<Conversation> findAll(String sortField, boolean ascending) {
        Sort sort = ascending
                ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);
        return springDataConversationRepository.findAll(sort);
    }

    @Override
    public boolean deleteById(String id) {
        springDataConversationRepository.deleteById(id);
        return true;
    }
}
