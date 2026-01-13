package software.aoc.day06.a.service;

import java.math.BigInteger;
import java.util.List;
import software.aoc.day06.a.model.MathProblem;
import software.aoc.day06.a.parser.WorksheetParser;

public class TrashCompactorService {

    private final WorksheetParser parser;

    public TrashCompactorService() {
        this.parser = new WorksheetParser();
    }

    public BigInteger calculateGrandTotal(List<String> rawLines) {
        List<MathProblem> problems = parser.parse(rawLines);

        return problems.stream()
                .map(MathProblem::solve)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
