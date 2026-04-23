package com.demo.agent_ai.knowledge.infrastructure.pdf;

import com.demo.agent_ai.knowledge.domain.models.UploadedFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PdfTextExtractor {

    public String extract(UploadedFile file) {
        try (PDDocument document = Loader.loadPDF(file.getContent())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse PDF", e);
        }
    }

    public String normalize(String text) {
        if (text == null) {
            return "";
        }

        String normalized = text
                // Unification ENTER signs 
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                // Delete lines that consist only of digits (page numbers) or common footer/header patterns
                .replaceAll("(?m)^\\s*\\d+\\s*$", "")
                // Delete non-printable characters
                .replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "")
                // Replace multiple spaces/tabs with a single space
                .replaceAll("[ \t]+", " ");

        // HEURISTIC FOR JOINING LINES:
        // If a line ends with a letter/comma and the next line starts with a lowercase letter,
        // it's likely one sentence broken in half.
        // We replace: "word\ncontinuation" -> "word continuation"
        // But we keep: "End.\nNew" -> "End.\nNew"

        // Step 1: Replace multiple (2 or more) newlines with a temporary token
        normalized = normalized.replaceAll("\n{2,}", "||PARAGRAPH||");

        // Step 2: Replace single newlines that are likely to be line breaks within sentences with spaces
        // (we assume that if there is no ||PARAGRAPH||, then it was a single \n)
        normalized = normalized.replace("\n", " ");

        // Krok 3: Przywróć akapity (zamień token na \n\n)
        // Step 3: Restore paragraphs (replace token with \n\n)
        normalized = normalized.replace("||PARAGRAPH||", "\n\n");

        return normalized.trim();
    }

    public String hash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
