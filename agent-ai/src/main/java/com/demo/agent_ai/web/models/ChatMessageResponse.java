package com.demo.agent_ai.web.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class ChatMessageResponse {
    private String role;
    private Date date;
    private String content;
}
