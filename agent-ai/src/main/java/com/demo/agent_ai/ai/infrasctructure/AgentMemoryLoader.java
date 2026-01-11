package com.demo.agent_ai.ai.infrasctructure;

import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AgentMemoryLoader {

    public ChatMemory loadMemory(List<ChatMessage> messages, String knowledgeContext) {
        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(50);
        if (StringUtils.hasText(knowledgeContext)) {
            memory.add(SystemMessage.from(knowledgeContext));
        }
        messages.forEach(m -> {
            if (ChatMessageRole.USER.getChatMessageRole().equalsIgnoreCase(m.getRole().getChatMessageRole())) {
                memory.add(UserMessage.from(m.getContent()));
            } else if (ChatMessageRole.AGENT.getChatMessageRole().equalsIgnoreCase(m.getRole().getChatMessageRole())) {
                memory.add(AiMessage.from(m.getContent()));
            }
        });

        return memory;
    }
}
