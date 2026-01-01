package com.demo.agent_ai.ai.application.factories;

import com.demo.agent_ai.ai.infrasctructure.AgentAiApi;
import com.demo.agent_ai.config.OllamaProperties;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Component;

@Component
public class AgentAiFactory {

    private final OllamaProperties ollamaProperties;
    private final ChatModel chatModel;
    private final ChatMemory memory;

    public AgentAiFactory(OllamaProperties ollamaProperties) {
        this.ollamaProperties = ollamaProperties;
        this.memory = MessageWindowChatMemory.withMaxMessages(20);
        this.chatModel = OllamaChatModel.builder()
                .baseUrl(ollamaProperties.getHost())
                .modelName(ollamaProperties.getModel())
                .timeout(java.time.Duration.ofSeconds(ollamaProperties.getTimeout()))
                .build();
    }

    public AgentAiApi createAgent() {
        return AiServices.builder(AgentAiApi.class)
                .chatModel(this.chatModel)
                .tools()
                .build();
    }

    public AgentAiApi createAgentWithMemory() {
        return AiServices.builder(AgentAiApi.class)
                .chatModel(this.chatModel)
                .chatMemory(this.memory)
                .build();
    }
}