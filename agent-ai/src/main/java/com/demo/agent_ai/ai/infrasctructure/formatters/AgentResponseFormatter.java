package com.demo.agent_ai.ai.infrasctructure.formatters;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AgentResponseFormatter {

    // np. "1.tekst", "12)tekst"
    private static final Pattern ORDERED_LIST_TIGHT =
            Pattern.compile("^(\\s*)(\\d+)([\\.)])(\\S.*)$");

    // np. "-tekst", "*tekst", "+tekst"
    private static final Pattern BULLET_LIST_TIGHT =
            Pattern.compile("^(\\s*)([-*+])(\\S.*)$");

    // poprawna lista numerowana
    private static final Pattern ORDERED_LIST_LINE =
            Pattern.compile("^\\s*\\d+[\\.)]\\s+.*$");

    // poprawna lista punktowana
    private static final Pattern BULLET_LIST_LINE =
            Pattern.compile("^\\s*[-*+]\\s+.*$");

    public String normalize(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }

        // 1) Ujednolicenie końców linii
        String normalized = raw.replace("\r\n", "\n").replace("\r", "\n");

        String[] lines = normalized.split("\n", -1);
        List<String> out = new ArrayList<>();

        for (String line : lines) {
            String processed = line;

            // 2) "1.tekst" -> "1. tekst", "1)tekst" -> "1) tekst"
            processed = ORDERED_LIST_TIGHT.matcher(processed)
                    .replaceFirst("$1$2$3 $4");

            // 3) "-tekst" -> "- tekst", "*tekst" -> "* tekst"
            processed = BULLET_LIST_TIGHT.matcher(processed)
                    .replaceFirst("$1$2 $3");

            out.add(processed);
        }

        // 4) Dodanie pustej linii przed listą (jeśli brak)
        out = ensureBlankLineBeforeLists(out);

        // 5) Maksymalnie jedna pusta linia pod rząd
        out = collapseBlankLines(out);

        return String.join("\n", out).trim();
    }

    private List<String> ensureBlankLineBeforeLists(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String current = lines.get(i);

            boolean isList = isListLine(current);
            if (isList && !result.isEmpty()) {
                String prev = result.get(result.size() - 1);
                if (!prev.isBlank()) {
                    result.add(""); // wstaw pustą linię przed listą
                }
            }
            result.add(current);
        }

        return result;
    }

    private List<String> collapseBlankLines(List<String> lines) {
        List<String> result = new ArrayList<>();
        boolean previousBlank = false;

        for (String line : lines) {
            boolean currentBlank = line.isBlank();
            if (currentBlank && previousBlank) {
                continue;
            }
            result.add(line);
            previousBlank = currentBlank;
        }

        return result;
    }

    private boolean isListLine(String line) {
        return ORDERED_LIST_LINE.matcher(line).matches()
                || BULLET_LIST_LINE.matcher(line).matches();
    }
}
