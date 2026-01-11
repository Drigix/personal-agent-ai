package com.demo.agent_ai.knowledge.application;

import com.demo.agent_ai.knowledge.application.port.in.KnowledgeIngestPort;
import com.demo.agent_ai.knowledge.domain.models.KnowledgeChunk;
import com.demo.agent_ai.knowledge.domain.models.KnowledgeDocument;
import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import com.demo.agent_ai.knowledge.domain.repository.KnowledgeChunkRepository;
import com.demo.agent_ai.knowledge.domain.repository.KnowledgeDocumentRepository;
import com.demo.agent_ai.knowledge.infrastructure.pdf.PdfTextExtractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KnowledgeIngestService implements KnowledgeIngestPort {

    private final PdfTextExtractor pdfTextExtractor;
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final KnowledgeChunkRepository knowledgeChunkRepository;

    @Override
    public String ingestFiles(String conversationId, List<UploadedFile> files, String chatMessage) {
        List<KnowledgeChunk> savedKnowledgeChunks = new LinkedList<>();
        for(UploadedFile file: files) {
            String rawText = pdfTextExtractor.extract(file);
            String normalized = pdfTextExtractor.normalize(rawText);
            String hash = pdfTextExtractor.hash(normalized);
            if (knowledgeDocumentRepository.isContentHashExistInConversation(conversationId, hash)) {
                continue;
            }
            KnowledgeDocument document = knowledgeDocumentRepository.save(
                    KnowledgeDocument.builder()
                            .conversationId(conversationId)
                            .filename(file.getFilename())
                            .contentHash(hash)
                            .date(new Date())
                            .build()
            );

            List<String> chunks = chunk(normalized, 500);
            for (String chunk : chunks) {
                KnowledgeChunk newKnowledgeChunk = knowledgeChunkRepository.save(
                        KnowledgeChunk.builder()
                                .documentId(document.getId())
                                .conversationId(conversationId)
                                .content(chunk)
                                .build()
                );
                savedKnowledgeChunks.add(newKnowledgeChunk);
            }
        }
        return buildKnowledgeContext(savedKnowledgeChunks);
    }

    @Override
    public String getConversationKnowledgeContext(String conversationId) {
        List<KnowledgeChunk> knowledgeChunks = knowledgeChunkRepository.findAllByConversationId(conversationId);
        if (knowledgeChunks == null || knowledgeChunks.isEmpty()) {
            return null;
        }
        return buildKnowledgeContext(knowledgeChunks);
    }

    @Override
    public void deleteKnowledgeByConversationId(String conversationId) {
        knowledgeChunkRepository.deleteByConversationId(conversationId);
        knowledgeDocumentRepository.deleteByConversationId(conversationId);
    }

    private String buildKnowledgeContext(List<KnowledgeChunk> chunks) {

        return """
        You have access to the following reference materials provided by the user.
        Use them if relevant. Do not invent facts beyond them.
    
        --- KNOWLEDGE START ---
        %s
        --- KNOWLEDGE END ---
        """.formatted(
                    chunks.stream()
                            .map(KnowledgeChunk::getContent)
                            .collect(Collectors.joining("\n\n"))
        );
    }

    private List<String> chunk(String text, int maxChars) {
        List<String> chunks = new ArrayList<>();
        int start = 0;

        while (start < text.length()) {
            int end = Math.min(text.length(), start + maxChars);
            chunks.add(text.substring(start, end));
            start = end;
        }

        return chunks;
    }
}
