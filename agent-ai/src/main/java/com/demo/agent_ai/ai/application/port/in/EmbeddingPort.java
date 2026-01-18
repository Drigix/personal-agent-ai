package com.demo.agent_ai.ai.application.port.in;

public interface EmbeddingPort {
    float[] embed(String text);
}
