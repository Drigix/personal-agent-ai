package com.demo.agent_ai.chat.application;

import com.demo.agent_ai.chat.application.port.out.LlmClient;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.chat.domain.models.Conversation;
import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import com.demo.agent_ai.chat.domain.models.FileMetadata;
import com.demo.agent_ai.chat.domain.repository.ChatMessageRepository;
import com.demo.agent_ai.chat.domain.repository.ConversationRepository;
import com.demo.agent_ai.knowledge.application.port.in.KnowledgeIngestPort;
import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
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
    private final KnowledgeIngestPort knowledgeIngestPort;

    public ChatMessage chat(ChatRequestBody requestBody, List<UploadedFile> files) {
        try {
            if (StringUtils.hasText(requestBody.getConversationId())) {
                return existConversationChat(requestBody, files);
            } else {
                return newConversationChat(requestBody, files);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Conversation> getConversations() {
        return conversationRepository.findAll("date", false);
    }

    public List<ChatMessage> getChatHistoryByConversationId(String conversationId) {
        return chatMessageRepository.findByConversationId(conversationId);
    }

    public void deleteConversation(String conversationId) {
        knowledgeIngestPort.deleteKnowledgeByConversationId(conversationId);
        chatMessageRepository.deleteByConversationId(conversationId);
        conversationRepository.deleteById(conversationId);
    }

    private ChatMessage newConversationChat(ChatRequestBody requestBody, List<UploadedFile> files) {
        Conversation newConversation = saveConversation(requestBody.getTitle());
        String conversationId = newConversation.getId();
        saveChatMessage(
                conversationId,
                requestBody.getDate(),
                ChatMessageRole.USER,
                requestBody.getChatMessage(),
                prepareUploadedFilesMetadata(files)
        );
        String conversationKnowledgeContext = null;
        if (files != null && !files.isEmpty()) {
            conversationKnowledgeContext = knowledgeIngestPort.ingestFiles(conversationId, files, requestBody.getChatMessage());
        }
        String response = llmClient.generateResponse(null, conversationKnowledgeContext, requestBody.getChatMessage());
        ChatMessage responseBody = saveChatMessage(
                conversationId,
                new Date(),
                ChatMessageRole.AGENT,
                response,
                null
        );
        return responseBody;
    }

    private ChatMessage existConversationChat(ChatRequestBody requestBody, List<UploadedFile> files) {
        String conversationId = requestBody.getConversationId();
        List<ChatMessage> historyContexts = chatMessageRepository.findLastByConversationId(conversationId, 20);
        if (files != null && !files.isEmpty()) {
            knowledgeIngestPort.ingestFiles(conversationId, files, requestBody.getChatMessage());
        }
        String conversationKnowledgeContext = knowledgeIngestPort.getConversationKnowledgeContext(conversationId);
        String response = llmClient.generateResponse(historyContexts, conversationKnowledgeContext, requestBody.getChatMessage());
        ChatMessage responseBody = saveChatMemory(requestBody, files, response);
        return responseBody;
    }

    private ChatMessage saveChatMemory(
            ChatRequestBody requestBody,
            List<UploadedFile> files,
            String response) {
        String conversationId = requestBody.getConversationId();
        if (!StringUtils.hasText(conversationId)) {
            Conversation newConversation = saveConversation(requestBody.getTitle());
            conversationId = newConversation.getId();
        }

        saveChatMessage(
                conversationId,
                requestBody.getDate(),
                ChatMessageRole.USER,
                requestBody.getChatMessage(),
                prepareUploadedFilesMetadata(files)
        );

        ChatMessage newAgentChatMessage = saveChatMessage(
                conversationId,
                new Date(),
                ChatMessageRole.AGENT,
                response,
                null
        );
        return newAgentChatMessage;
    }

    private Conversation saveConversation(String title) {
        Conversation newConversation = Conversation.builder()
                .title(title)
                .build();
        return conversationRepository.save(newConversation);
    }

    private ChatMessage saveChatMessage(
            String conversationId,
            Date date,
            ChatMessageRole role,
            String content,
            List<FileMetadata> fileMetadata
    ) {
        ChatMessage chatMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .date(date)
                .role(role)
                .content(content)
                .fileMetadata(fileMetadata)
                .build();
        return chatMessageRepository.save(chatMessage);
    }

    private List<FileMetadata> prepareUploadedFilesMetadata(List<UploadedFile> files) {
        if (files == null || files.isEmpty()) {
            return null;
        }
        return (List<FileMetadata>) files.stream()
                .map(file ->
                        FileMetadata.builder()
                                .filename(file.getFilename())
                                .size(file.roundSizeToMb())
                                .contentType(file.getContentType())
                                .build())
                .toList();
    }
}
