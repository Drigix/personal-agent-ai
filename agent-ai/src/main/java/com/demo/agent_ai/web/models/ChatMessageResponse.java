package com.demo.agent_ai.web.models;

import com.demo.agent_ai.chat.domain.models.FileMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private String conversationId;
    private String role;
    private Date date;
    private String content;
    private List<FileMetadata> fileMetadata;
}
