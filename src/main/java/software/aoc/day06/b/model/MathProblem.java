package software.aoc.day06.b.model;

import java.math.BigInteger;
import java.util.List;

public record MathProblem(List<BigInteger> operands, char operator) {

    public BigInteger solve() {
        if (operands == null || operands.isEmpty()) {
            return BigInteger.ZERO;
        }

        if (operator == '+') {
            return operands.stream()
                    .reduce(BigInteger.ZERO, BigInteger::add);
        } else if (operator == '*') {
            return operands.stream()
                    .reduce(BigInteger.ONE, BigInteger::multiply);
        } else {
            throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
