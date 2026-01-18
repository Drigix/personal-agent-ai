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
            if (current.length() + p.length() > MAX_CHARS) {
                chunks.add(current.toString().trim());

                // overlap
                int start = Math.max(0, current.length() - OVERLAP);
                current = new StringBuilder(current.substring(start));
            }

            current.append(p).append("\n\n");
        }

        if (!current.isEmpty()) {
            chunks.add(current.toString().trim());
        }

        return chunks;
    }
}
