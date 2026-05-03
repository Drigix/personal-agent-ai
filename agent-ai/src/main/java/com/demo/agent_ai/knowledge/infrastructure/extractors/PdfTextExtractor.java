package com.demo.agent_ai.knowledge.infrastructure.extractors;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PdfTextExtractor extends AbstractFileProcessor {

    @Override
    public boolean supports(UploadedFile file) {
        return "application/pdf".equals(file.getContentType());
    }

    @Override
    public String extract(UploadedFile file) {
        try (PDDocument document = Loader.loadPDF(file.getContent())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse PDF", e);
        }
    }
}
