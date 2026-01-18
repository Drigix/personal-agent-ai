package com.demo.agent_ai.ai.application.factories;

import com.demo.agent_ai.ai.infrasctructure.AgentAiApi;
import com.demo.agent_ai.ai.infrasctructure.AgentMemoryLoader;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import com.demo.agent_ai.config.OllamaProperties;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AgentAiFactory {

    private final OllamaProperties ollamaProperties;
    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final AgentMemoryLoader agentMemoryLoader;

    public AgentAiFactory(OllamaProperties ollamaProperties, AgentMemoryLoader agentMemoryLoader) {
        this.ollamaProperties = ollamaProperties;
        this.agentMemoryLoader = agentMemoryLoader;
        this.chatModel = OllamaChatModel.builder()
                .baseUrl(ollamaProperties.getHost())
                .modelName(ollamaProperties.getModel())
                .timeout(java.time.Duration.ofSeconds(ollamaProperties.getTimeout()))
                .build();
        this.embeddingModel = OllamaEmbeddingModel.builder()
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

    public AgentAiApi createAgentWithMemory(List<ChatMessage> history, String knowledgeContext) {
        ChatMemory memory = agentMemoryLoader.loadMemory(history);
        return AiServices.builder(AgentAiApi.class)
                .chatModel(this.chatModel)
                .chatMemory(memory)
                .systemMessageProvider(memoryId -> knowledgeContext)
                .build();
    }

    public EmbeddingModel createEmbeddingModel() {
        return this.embeddingModel;
    }
}