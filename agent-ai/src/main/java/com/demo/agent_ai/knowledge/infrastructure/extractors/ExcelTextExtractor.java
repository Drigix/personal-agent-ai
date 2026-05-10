package com.demo.agent_ai.knowledge.infrastructure.extractors;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class ExcelTextExtractor extends AbstractFileProcessor {

    @Override
    public boolean supports(UploadedFile file) {
        if (file == null || file.getFilename() == null) return false;

        String filename = file.getFilename().toLowerCase();
        String contentType = file.getContentType();

        boolean hasExcelContentType = contentType != null &&
                (contentType.contains("ms-excel") || contentType.contains("spreadsheetml"));

        return hasExcelContentType || filename.endsWith(".xls") || filename.endsWith(".xlsx");
    }

    @Override
    protected String extract(UploadedFile file) {
        try (InputStream is = new ByteArrayInputStream(file.getContent());
             POITextExtractor extractor = ExtractorFactory.createExtractor(is)) {

            return extractor.getText();

        } catch (Exception e) {
            throw new RuntimeException("Cannot parse Excel document", e);
        }
    }
}
