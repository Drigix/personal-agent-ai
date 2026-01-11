package com.demo.agent_ai.chat.domain.models;

import com.demo.agent_ai.chat.domain.enums.ChatMessageRole;
import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "chat_messages")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    private String id;

    private String conversationId;

    private ChatMessageRole role;

    private Date date;

    private String content;

    @Nullable
    private List<FileMetadata> fileMetadata;
}
