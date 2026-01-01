package com.demo.agent_ai.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private String conversationId;
    private String role;
    private Date date;
    private String content;
}
