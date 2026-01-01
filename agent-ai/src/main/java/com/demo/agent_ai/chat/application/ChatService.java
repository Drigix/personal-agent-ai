package com.demo.agent_ai.chat.application;

import com.demo.agent_ai.chat.application.port.out.LlmClient;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import com.demo.agent_ai.chat.domain.repository.ChatMessageRepository;
import com.demo.agent_ai.chat.domain.repository.ConversationRepository;
import com.demo.agent_ai.web.models.ChatRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    private final LlmClient llmClient;
    private final ConversationRepository conversationRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage chat(ChatRequestBody requestBody) {
        try {
            String response = llmClient.generateResponse(requestBody.getChatMessage());
            ChatMessage responseBody = saveChatMemory(requestBody, response);
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Conversation> getConversations() {
        return conversationRepository.findAll("date", true);
    }

    public List<ChatMessage> getChatHistoryByConversationId(String conversationId) {
        return chatMessageRepository.findByConversationId(conversationId);
    }

    public void deleteConversation(String conversationId) {
        chatMessageRepository.deleteByConversationId(conversationId);
        conversationRepository.deleteById(conversationId);
    }

    private ChatMessage saveChatMemory(ChatRequestBody requestBody, String response) {
        String conversationId = requestBody.getConversationId();
        if (!StringUtils.hasText(conversationId)) {
            Conversation newConversation = Conversation.builder()
                    .title(requestBody.getTitle())
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
        return newAgentChatMessage;
    }
}
