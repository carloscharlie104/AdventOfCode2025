package software.aoc.day05.a.parser;

import java.math.BigInteger;
import software.aoc.day05.a.model.FreshnessRange;

public class RangeParser {

    public FreshnessRange parse(String rawRange) {
        String[] parts = rawRange.trim().split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + rawRange);
        }
        return new FreshnessRange(
                new BigInteger(parts[0]),
                new BigInteger(parts[1]));
    }
}
