package com.demo.agent_ai.chat.domain.enums;

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

    public static ChatMessageRole fromCode(String code) {
        for (ChatMessageRole role : values()) {
            if (role. value.equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
