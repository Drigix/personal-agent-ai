package com.demo.agent_ai.chat.infrasctructure.repository;

import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.chat.domain.repository.ChatMessageRepository;
import com.demo.agent_ai.chat.infrasctructure.adapters.SpringDataChatMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final SpringDataChatMessageRepository springDataChatMessageRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return springDataChatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findByConversationId(String conversationId) {
        return springDataChatMessageRepository.findByConversationId(conversationId);
    }

    @Override
    public List<ChatMessage> findLastByConversationId(String conversationId, Integer limit) {
        Pageable pageable = PageRequest.of(
                0,
                limit != null ? limit : 20,
                Sort.by(Sort.Direction.DESC, "date")
        );

        return springDataChatMessageRepository.findByConversationId(conversationId, pageable);
    }

    @Override
    public boolean deleteByConversationId(String conversationId) {
        springDataChatMessageRepository.deleteByConversationId(conversationId);
        return true;
    }
}
