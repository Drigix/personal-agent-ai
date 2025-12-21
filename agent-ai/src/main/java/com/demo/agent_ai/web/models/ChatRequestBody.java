package com.demo.agent_ai.web.models;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class ChatRequestBody {
    private String chatMessage;
    private String conversationId;
    private Date date;
}
