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
public class ChatRequestBody {
    private String title;
    private String chatMessage;
    private String conversationId;
    private Date date;
}
