package com.demo.agent_ai.ai.infrasctructure;

import com.demo.agent_ai.ai.application.factories.AgentAiFactory;
import com.demo.agent_ai.chat.application.port.out.LlmClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OllamaLlmClient implements LlmClient {

    private final AgentAiFactory agentAiFactory;

    @Override
    public String generateResponse(String context) {
        AgentAiApi agentAiApi = agentAiFactory.createAgentWithMemory();
        return agentAiApi.chat(context);
    }
}
