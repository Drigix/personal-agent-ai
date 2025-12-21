package com.demo.agent_ai.shared.application;

import com.demo.agent_ai.shared.domain.documents.ChatMessage;
import com.demo.agent_ai.shared.domain.documents.Conversation;
import com.demo.agent_ai.web.models.ChatRequestBody;

import java.util.List;

public interface ChatService {
    String chat(ChatRequestBody requestBody);

    List<Conversation> getConversations();

    List<ChatMessage> getChatHistoryByConversationId(String conversationId);
}
