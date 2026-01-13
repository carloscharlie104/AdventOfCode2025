package software.aoc.day02.b.parser;

import java.math.BigInteger;
import software.aoc.day02.b.model.ProductRange;

public class RangeParser {

    public ProductRange parse(String rangeSegment) {
        String[] parts = rangeSegment.trim().split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + rangeSegment);
        }
        return new ProductRange(
                new BigInteger(parts[0]),
                new BigInteger(parts[1]));
    }
}
