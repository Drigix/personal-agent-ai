package com.demo.agent_ai.knowledge.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("knowledge_documents")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocument {

    @Id
    private String id;

    private String conversationId;

    private String filename;

    private String contentHash;

    @CreatedDate
    private Date date;
}
