package com.demo.agent_ai.chat.infrasctructure.adapters;

import com.demo.agent_ai.chat.domain.models.Conversation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataConversationRepository extends MongoRepository<Conversation, String>{
    List<Conversation> findAllByUserDataId(Long userDataId, Sort sort);
    List<Conversation> findAllByUserDataIdOrderByDateDesc(Long userDataId);
}
