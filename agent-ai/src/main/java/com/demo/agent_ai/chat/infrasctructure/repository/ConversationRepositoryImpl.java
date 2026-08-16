package com.demo.agent_ai.chat.infrasctructure.repository;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.chat.domain.repository.ConversationRepository;
import com.demo.agent_ai.chat.infrasctructure.adapters.SpringDataConversationRepository;
import com.demo.agent_ai.utils.SortField;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    public List<Conversation> findAllByUserDataId(Long userDataId, SortField sortField) {
        if (sortField == null) {
            return springDataConversationRepository.findAllByUserDataIdOrderByDateDesc(userDataId);
        }
        Sort sort = Sort.by(sortField.getDirection(), sortField.getField());
        return springDataConversationRepository.findAllByUserDataId(userDataId, sort);
    }

    @Override
    public List<Conversation> findAll(@NonNull SortField sortField) {
        Sort sort = Sort.by(sortField.getDirection(), sortField.getField());
        return springDataConversationRepository.findAll(sort);
    }

    @Override
    public boolean deleteById(String id) {
        springDataConversationRepository.deleteById(id);
        return true;
    }
}
