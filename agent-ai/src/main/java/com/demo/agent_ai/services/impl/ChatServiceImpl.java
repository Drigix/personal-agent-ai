package com.demo.agent_ai.services.impl;

import com.demo.agent_ai.ai.agent.AgentAiApi;
import com.demo.agent_ai.services.ChatService;
import com.demo.agent_ai.ai.factories.AgentAiFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final AgentAiFactory agentAiFactory;

    public ChatServiceImpl(
        AgentAiFactory agentAiFactory
    ) {
        this.agentAiFactory = agentAiFactory;
    }

    @Override
    public String chat(String message) {
        AgentAiApi agentAiApi = agentAiFactory.createAgentWithMemory();
        return agentAiApi.chat(message);
    }
}
