package com.demo.agent_ai.ai.application;

import com.demo.agent_ai.ai.application.factories.AgentAiFactory;
import com.demo.agent_ai.ai.application.port.in.EmbeddingPort;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingService implements EmbeddingPort {

    private final AgentAiFactory agentAiFactory;

    @Override
    public float[] embed(String text) {
        EmbeddingModel embeddingModel = agentAiFactory.createEmbeddingModel();
        Embedding embedding = embeddingModel.embed(text).content();
        return embedding.vector();
    }
}
