package software.aoc.day05.b.parser;

import java.math.BigInteger;
import software.aoc.day05.b.model.FreshnessRange;

public class RangeParser {

    public FreshnessRange parse(String rawRange) {
        String[] parts = rawRange.trim().split("-");
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid range format: " + rawRange);
        BigInteger a = new BigInteger(parts[0]);
        BigInteger b = new BigInteger(parts[1]);
        return new FreshnessRange(a.min(b), a.max(b));
    }
}
