package software.aoc.day06.a.parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import software.aoc.day06.a.model.MathProblem;

public class WorksheetParser {

    public List<MathProblem> parse(List<String> lines) {
        List<MathProblem> problems = new ArrayList<>();
        if (lines == null || lines.isEmpty())
            return problems;

        int width = lines.stream().mapToInt(String::length).max().orElse(0);

        List<String> paddedLines = new ArrayList<>();
        for (String line : lines) {
            paddedLines.add(String.format("%-" + width + "s", line));
        }
        lines = paddedLines;

        boolean insideProblem = false;
        int currentStartCol = -1;

        for (int col = 0; col < width; col++) {
            boolean isColEmpty = isColumnEmpty(lines, col);

            if (!isColEmpty) {
                if (!insideProblem) {
                    insideProblem = true;
                    currentStartCol = col;
                }
            } else {
                if (insideProblem) {
                    problems.add(extractProblem(lines, currentStartCol, col - 1));
                    insideProblem = false;
                }
            }
        }

        if (insideProblem) {
            problems.add(extractProblem(lines, currentStartCol, width - 1));
        }

        return problems;
    }

    private boolean isColumnEmpty(List<String> lines, int col) {
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                return false;
            }
        }
        return true;
    }

    private MathProblem extractProblem(List<String> lines, int startCol, int endCol) {
        List<BigInteger> operands = new ArrayList<>();
        char operator = ' ';

        int height = lines.size();

        for (int r = 0; r < height - 1; r++) {
            String line = lines.get(r);
            String segment = safeSubstring(line, startCol, endCol).trim();
            if (!segment.isEmpty()) {
                operands.add(new BigInteger(segment));
            }
        }

        String opLine = lines.get(height - 1);
        String opSegment = safeSubstring(opLine, startCol, endCol).trim();
        if (!opSegment.isEmpty()) {
            operator = opSegment.charAt(0);
        }

        return new MathProblem(operands, operator);
    }

    private String safeSubstring(String line, int start, int end) {
        if (start >= line.length())
            return "";
        int actualEnd = Math.min(end + 1, line.length());
        return line.substring(start, actualEnd);
    }
}
