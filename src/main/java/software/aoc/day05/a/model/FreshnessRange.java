package software.aoc.day05.a.model;

import java.math.BigInteger;

public record FreshnessRange(BigInteger start, BigInteger end) {

    public boolean contains(BigInteger id) {
        return id.compareTo(start) >= 0 && id.compareTo(end) <= 0;
    }
}
