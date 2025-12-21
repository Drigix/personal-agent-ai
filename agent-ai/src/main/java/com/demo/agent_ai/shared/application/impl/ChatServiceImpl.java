package com.demo.agent_ai.shared.application.impl;

import com.demo.agent_ai.ai.agent.AgentAiApi;
import com.demo.agent_ai.shared.application.ChatService;
import com.demo.agent_ai.ai.factories.AgentAiFactory;
import com.demo.agent_ai.shared.domain.documents.ChatMessage;
import com.demo.agent_ai.shared.domain.documents.Conversation;
import com.demo.agent_ai.shared.domain.enums.ChatMessageRole;
import com.demo.agent_ai.shared.infrasctructure.repository.ChatMessageRepository;
import com.demo.agent_ai.shared.infrasctructure.repository.ConversationRepository;
import com.demo.agent_ai.web.models.ChatRequestBody;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final AgentAiFactory agentAiFactory;
    private final ConversationRepository conversationRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatServiceImpl(
        AgentAiFactory agentAiFactory,
        ConversationRepository conversationRepository,
        ChatMessageRepository chatMessageRepository
    ) {
        this.agentAiFactory = agentAiFactory;
        this.conversationRepository = conversationRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public String chat(ChatRequestBody requestBody) {
        try {
            AgentAiApi agentAiApi = agentAiFactory.createAgentWithMemory();
            String response = agentAiApi.chat(requestBody.getChatMessage());
            saveChatMemory(requestBody, response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Conversation> getConversations() {
        return List.of();
    }

    @Override
    public List<ChatMessage> getChatHistoryByConversationId(String conversationId) {
        return List.of();
    }

    private void saveChatMemory(ChatRequestBody requestBody, String response) {
        String conversationId = requestBody.getConversationId();
        if (!StringUtils.hasText(conversationId)) {
            Conversation newConversation = Conversation.builder()
                    .title("test")
                    .build();
            newConversation = conversationRepository.save(newConversation);
            conversationId = newConversation.getId();
        }
        ChatMessage newUserChatMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .date(requestBody.getDate())
                .role(ChatMessageRole.USER)
                .content(requestBody.getChatMessage())
                .build();
        newUserChatMessage = chatMessageRepository.save(newUserChatMessage);

        ChatMessage newAgentChatMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .date(new Date())
                .role(ChatMessageRole.AGENT)
                .content(response)
                .build();
        newAgentChatMessage = chatMessageRepository.save(newAgentChatMessage);
    }
}
