package com.demo.agent_ai.knowledge.application.port.in;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;

import java.util.List;

public interface KnowledgeIngestPort {
    String ingestFiles(
            String conversationId,
            List<UploadedFile> files,
            String chatMessage
    );

    String getConversationKnowledgeContext(String conversationId);

    void deleteKnowledgeByConversationId(String conversationId);
}
