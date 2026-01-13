package software.aoc.day06.b.parser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import software.aoc.day06.b.model.MathProblem;

public class WorksheetParser {

    public enum Mode {
        HORIZONTAL_ROWS,
        VERTICAL_COLS
    }

    public List<MathProblem> parse(List<String> lines, Mode mode) {
        List<MathProblem> problems = new ArrayList<>();
        if (lines == null || lines.isEmpty())
            return problems;

        int width = lines.stream().mapToInt(String::length).max().orElse(0);

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
                    problems.add(extractProblem(lines, currentStartCol, col - 1, mode));
                    insideProblem = false;
                }
            }
        }

        if (insideProblem) {
            problems.add(extractProblem(lines, currentStartCol, width - 1, mode));
        }

        return problems;
    }

    private MathProblem extractProblem(List<String> lines, int startCol, int endCol, Mode mode) {
        if (mode == Mode.HORIZONTAL_ROWS) {
            return extractHorizontal(lines, startCol, endCol);
        } else {
            return extractVertical(lines, startCol, endCol);
        }
    }

    private MathProblem extractHorizontal(List<String> lines, int startCol, int endCol) {
        List<BigInteger> operands = new ArrayList<>();
        int height = lines.size();

        for (int r = 0; r < height - 1; r++) {
            String segment = safeSubstring(lines.get(r), startCol, endCol).trim();
            if (!segment.isEmpty()) {
                operands.add(new BigInteger(segment));
            }
        }
        return new MathProblem(operands, extractOperator(lines, startCol, endCol));
    }

    private MathProblem extractVertical(List<String> lines, int startCol, int endCol) {
        List<BigInteger> operands = new ArrayList<>();
        int height = lines.size();

        for (int c = startCol; c <= endCol; c++) {
            StringBuilder numBuilder = new StringBuilder();

            for (int r = 0; r < height - 1; r++) {
                char ch = safeCharAt(lines.get(r), c);
                if (ch != ' ') {
                    numBuilder.append(ch);
                }
            }

            if (numBuilder.length() > 0) {
                operands.add(new BigInteger(numBuilder.toString()));
            }
        }
        return new MathProblem(operands, extractOperator(lines, startCol, endCol));
    }

    private char extractOperator(List<String> lines, int startCol, int endCol) {
        String opLine = lines.get(lines.size() - 1);
        String opSegment = safeSubstring(opLine, startCol, endCol).trim();
        return opSegment.isEmpty() ? '?' : opSegment.charAt(0);
    }

    private boolean isColumnEmpty(List<String> lines, int col) {
        for (String line : lines) {
            if (safeCharAt(line, col) != ' ')
                return false;
        }
        return true;
    }

    private String safeSubstring(String line, int start, int end) {
        if (start >= line.length())
            return "";
        int actualEnd = Math.min(end + 1, line.length());
        return line.substring(start, actualEnd);
    }

    private char safeCharAt(String line, int index) {
        if (index >= line.length())
            return ' ';
        return line.charAt(index);
    }
}
