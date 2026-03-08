package com.demo.agent_ai.knowledge.infrastructure.text;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextChunker {

    private static final int MAX_CHARS = 1200;
    private static final int OVERLAP = 200;

    public List<String> chunk(String text) {
        List<String> chunks = new ArrayList<>();

        String[] paragraphs = text.split("\n\n");
        StringBuilder current = new StringBuilder();

        for (String p : paragraphs) {
            // If p is larger than MAX_CHARS, we need to split it
            if (p.length() > MAX_CHARS) {
                // First flush current buffer if not empty
                if (!current.isEmpty()) {
                    chunks.add(current.toString().trim());
                    // Clear current buffer
                    current.setLength(0);
                }
                // In case of a very large paragraph, we can split it into multiple chunks with overlap
                chunks.addAll(splitLargeParagraph(p));
                continue;
            }

            // Logic: If adding this paragraph would exceed the limit, we save the current chunk and start a new one with an overlap
            if (current.length() + p.length() + 2 > MAX_CHARS) { // +2 for "\n\n"
                chunks.add(current.toString().trim());

                // Create an overlap from the END of what we just saved
                String justSaved = current.toString();
                current.setLength(0);

                if (justSaved.length() > OVERLAP) {
                    current.append(justSaved.substring(justSaved.length() - OVERLAP));
                } else {
                    current.append(justSaved);
                }
            }

            if (!current.isEmpty()) {
                current.append("\n\n");
            }
            current.append(p);
        }

        if (!current.isEmpty()) {
            chunks.add(current.toString().trim());
        }

        return chunks;
    }

    private List<String> splitLargeParagraph(String text) {
        List<String> subChunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + MAX_CHARS, text.length());
            subChunks.add(text.substring(start, end));
            start += (MAX_CHARS - OVERLAP);
        }
        return subChunks;
    }
}
