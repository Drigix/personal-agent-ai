package com.demo.agent_ai.knowledge.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("knowledge_chunks")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeChunk {

    @Id
    private String id;

    private String documentId;

    private String conversationId;

    private String content;

    // private float[] embedding;
}
