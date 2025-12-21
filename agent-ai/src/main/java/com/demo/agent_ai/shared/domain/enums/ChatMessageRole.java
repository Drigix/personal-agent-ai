package com.demo.agent_ai.shared.domain.enums;

import lombok.Setter;

public enum ChatMessageRole {

    USER("user"),
    AGENT("agent");

    private final String value;

    private ChatMessageRole(String value) {
        this.value = value;
    }

    public String getChatMessageRole() {
        return value;
    }
}
