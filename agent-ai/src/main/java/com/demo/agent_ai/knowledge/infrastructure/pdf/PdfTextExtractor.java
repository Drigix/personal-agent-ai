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
                return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse PDF", e);
        }
    }

    public String normalize(String text) {
        if (text == null) {
            return "";
        }

        return text
                // normalize end of the lines
                .replace("\r\n", "\n")
                .replace("\r", "\n")

                // delete many tabs and spaces
                .replaceAll("[ \t]+", " ")

                // reduction of empty lines
                .replaceAll("\n{3,}", "\n\n")

                // delete spaces in start and end of the line
                .replaceAll("(?m)^\\s+|\\s+$", "")

                // trim all
                .trim();
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
