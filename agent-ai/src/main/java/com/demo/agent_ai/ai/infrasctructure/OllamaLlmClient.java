package com.demo.agent_ai.ai.infrasctructure;

import com.demo.agent_ai.ai.application.factories.AgentAiFactory;
import com.demo.agent_ai.chat.application.port.out.LlmClient;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OllamaLlmClient implements LlmClient {

    private final AgentAiFactory agentAiFactory;

    @Override
    public String generateResponse(
            List<ChatMessage> historyContext,
            String conversationKnowledgeContext,
            String context
    ) {
        AgentAiApi agentAiApi = agentAiFactory.createAgentWithMemory(historyContext, conversationKnowledgeContext);
        return agentAiApi.chat(context);
    }
}
