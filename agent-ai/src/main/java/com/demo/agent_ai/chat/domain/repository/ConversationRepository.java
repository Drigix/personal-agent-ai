package com.demo.agent_ai.chat.domain.repository;

import com.demo.agent_ai.chat.domain.models.Conversation;

import java.util.List;

public interface ConversationRepository {
    Conversation save(Conversation conversation);
    List<Conversation> findAll(String sortField, boolean ascending);
    boolean deleteById(String id);
}
