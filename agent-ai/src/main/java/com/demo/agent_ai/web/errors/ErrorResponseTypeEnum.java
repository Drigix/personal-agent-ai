package com.demo.agent_ai.web.errors;

public enum ErrorResponseTypeEnum {

    JWT_EXPIRED("JWT_EXPIRED"),
    BAD_CREDENTIALS("BAD_CREDENTIALS");

    private ErrorResponseTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
