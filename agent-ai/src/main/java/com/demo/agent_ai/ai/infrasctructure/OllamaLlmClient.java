package com.demo.agent_ai.ai.infrasctructure;

import com.demo.agent_ai.ai.application.factories.AgentAiFactory;
import com.demo.agent_ai.ai.infrasctructure.formatters.AgentResponseFormatter;
import com.demo.agent_ai.chat.application.port.out.LlmClient;
import com.demo.agent_ai.chat.domain.models.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OllamaLlmClient implements LlmClient {

    private final AgentAiFactory agentAiFactory;
    private final AgentResponseFormatter agentResponseFormatter;

    @Override
    public String generateResponse(
            List<ChatMessage> historyContext,
            String conversationKnowledgeContext,
            String context
    ) {
        AgentAiApi agentAiApi = agentAiFactory.createAgentWithMemory(historyContext);
        String rawResponse = agentAiApi.chat(concatKnowledgeContextAndQuestionContext(conversationKnowledgeContext, context));
        return agentResponseFormatter.normalize(rawResponse);
    }

    private String concatKnowledgeContextAndQuestionContext(String conversationKnowledgeContext, String userQuestion) {
        String finalPrompt;

        if (conversationKnowledgeContext != null) {
            finalPrompt = conversationKnowledgeContext + "\n\nUser Question: " + userQuestion;
        } else {
            finalPrompt = userQuestion;
        }
        return finalPrompt;
    }
}
