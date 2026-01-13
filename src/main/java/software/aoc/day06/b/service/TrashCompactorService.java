package software.aoc.day06.b.service;

import java.math.BigInteger;
import java.util.List;
import software.aoc.day06.b.model.MathProblem;
import software.aoc.day06.b.parser.WorksheetParser;

public class TrashCompactorService {

    private final WorksheetParser parser;

    public TrashCompactorService() {
        this.parser = new WorksheetParser();
    }

    public BigInteger calculateGrandTotal(List<String> rawLines) {
        return calculateTotal(rawLines, WorksheetParser.Mode.HORIZONTAL_ROWS);
    }

    public BigInteger calculateGrandTotalPart2(List<String> rawLines) {
        return calculateTotal(rawLines, WorksheetParser.Mode.VERTICAL_COLS);
    }

    private BigInteger calculateTotal(List<String> rawLines, WorksheetParser.Mode mode) {
        return parser.parse(rawLines, mode).stream()
                .map(MathProblem::solve)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
