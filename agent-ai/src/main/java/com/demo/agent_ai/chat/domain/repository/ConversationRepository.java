package com.demo.agent_ai.chat.domain.repository;

import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.utils.SortField;

import java.util.List;

public interface ConversationRepository {
    Conversation save(Conversation conversation);
    List<Conversation> findAll(SortField sortField);
    List<Conversation> findAllByUserDataId(Long userDataId, SortField sortField);
    boolean deleteById(String id);
}
