package com.demo.agent_ai.ai.infrasctructure.formatters;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AgentResponseFormatter {

    // "1.text" / "1)text"
    private static final Pattern ORDERED_LIST_TIGHT =
            Pattern.compile("^(\\s*)(\\d+)([\\.)])(\\S.*)$");

    // "-text" "*text" "+text"
    private static final Pattern BULLET_LIST_TIGHT =
            Pattern.compile("^(\\s*)([-*+])(\\S.*)$");

    // correct lists
    private static final Pattern ORDERED_LIST_LINE =
            Pattern.compile("^\\s*\\d+[\\.)]\\s+.*$");

    private static final Pattern BULLET_LIST_LINE =
            Pattern.compile("^\\s*[-*+]\\s+.*$");

    // "Title:" (line to headline)
    private static final Pattern TITLE_WITH_COLON =
            Pattern.compile("^\\s*\\*{0,2}\\s*([\\p{L}0-9][^:\\n]{1,120})\\s*\\*{0,2}:\\s*$");

    // "**Answer**: content"
    private static final Pattern INLINE_ANSWER_PREFIX =
            Pattern.compile("^\\s*\\*{0,2}(Odpowiedź|Podsumowanie|Wniosek|Rekomendacja)\\*{0,2}:\\s*(.+)$",
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    public String normalize(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }

        // 1) Normalize newline
        String normalized = raw.replace("\r\n", "\n").replace("\r", "\n").trim();

        // 2) Split
        String[] lines = normalized.split("\n", -1);
        List<String> out = new ArrayList<>();

        // Coding block ```...```
        boolean inCodeBlock = false;

        for (String originalLine : lines) {
            String line = originalLine;

            String trimmed = line.trim();
            if (trimmed.startsWith("```")) {
                inCodeBlock = !inCodeBlock;
                out.add(line);
                continue;
            }

            // Don't change context o  code blocks
            if (inCodeBlock) {
                out.add(line);
                continue;
            }

            // 3) Inline prefix like "**Answer**: something" -> headline + new line
            line = rewriteInlineAnswerPrefix(line, out);

            // If rewriteInlineAnswerPrefix added to out
            if (line == null) {
                continue;
            }

            // 4) Fix "squeezed" lists
            line = ORDERED_LIST_TIGHT.matcher(line).replaceFirst("$1$2. $4");
            line = BULLET_LIST_TIGHT.matcher(line).replaceFirst("$1- $3");

            // 5) Normalize bullets: "*" / "+" -> "-"
            if (BULLET_LIST_LINE.matcher(line).matches()) {
                line = line.replaceFirst("^(\\s*)[-*+](\\s+)", "$1- ");
            }

            out.add(line);
        }

        // 6) Change "Title:" for "## Title" when make it sense
        out = convertTitleLinesToHeadings(out);

        // 7) New empty line before list and headlines
        out = ensureBlankLineBeforeBlocks(out);

        // 8) New empty line after list and headlines
        out = ensureBlankLineAfterHeadings(out);

        // 9) Reduction of empty new linnes
        out = collapseBlankLines(out);

        // 10) Trim
        return String.join("\n", out).trim();
    }

    /**
     * Changing for example. "**Answer**: context" for:
     * ## Answer
     * context
     */
    private String rewriteInlineAnswerPrefix(String line, List<String> out) {
        var m = INLINE_ANSWER_PREFIX.matcher(line);
        if (m.matches()) {
            String title = capitalizeFirst(m.group(1).trim());
            String content = m.group(2).trim();

            out.add("## " + title);
            out.add("");
            out.add(content);
            return null;
        }
        return line;
    }

    private List<String> convertTitleLinesToHeadings(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String current = lines.get(i);
            String trimmed = current.trim();

            // Skip existing headlines
            if (trimmed.isBlank() || trimmed.startsWith("#")) {
                result.add(current);
                continue;
            }

            var m = TITLE_WITH_COLON.matcher(current);
            if (m.matches()) {
                String title = m.group(1).trim();

                // heuristic: convert to header if next reasonable line is list or text
                String nextNonBlank = findNextNonBlank(lines, i + 1);
                if (nextNonBlank != null) {
                    result.add("## " + stripMarkdownEmphasis(title));
                    continue;
                }
            }

            result.add(current);
        }

        return result;
    }

    private List<String> ensureBlankLineBeforeBlocks(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (String current : lines) {
            boolean isBlockStart = isListLine(current) || isHeadingLine(current);

            if (isBlockStart && !result.isEmpty()) {
                String prev = result.getLast();
                if (!prev.isBlank()) {
                    result.add("");
                }
            }

            result.add(current);
        }

        return result;
    }

    private List<String> ensureBlankLineAfterHeadings(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String current = lines.get(i);
            result.add(current);

            if (isHeadingLine(current)) {
                String next = (i + 1 < lines.size()) ? lines.get(i + 1) : null;
                if (next != null && !next.isBlank()) {
                    result.add("");
                }
            }
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

    private boolean isHeadingLine(String line) {
        return line.trim().matches("^#{1,6}\\s+.*$");
    }

    private String findNextNonBlank(List<String> lines, int startIdx) {
        for (int i = startIdx; i < lines.size(); i++) {
            String t = lines.get(i).trim();
            if (!t.isBlank()) {
                return t;
            }
        }
        return null;
    }

    private String stripMarkdownEmphasis(String s) {
        return s.replaceAll("^\\*{1,2}", "").replaceAll("\\*{1,2}$", "").trim();
    }

    private String capitalizeFirst(String input) {
        if (input == null || input.isBlank()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
