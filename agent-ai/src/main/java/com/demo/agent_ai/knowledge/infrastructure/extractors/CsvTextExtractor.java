package com.demo.agent_ai.knowledge.infrastructure.extractors;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CsvTextExtractor extends AbstractFileProcessor {

    @Override
    public boolean supports(UploadedFile file) {
        if (file == null || file.getFilename() == null) return false;

        String filename = file.getFilename().toLowerCase();
        return "text/csv".equals(file.getContentType()) || filename.endsWith(".csv");
    }

    @Override
    protected String extract(UploadedFile file) {
        return new String(file.getContent(), StandardCharsets.UTF_8);
    }
}
