package com.demo.agent_ai.ai.infrasctructure;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AgentAiApi {
    @UserMessage("{{it}}")
    String chat(String userMessage);
}
