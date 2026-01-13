package software.aoc.day05.b.model;

import java.math.BigInteger;

public record FreshnessRange(BigInteger start, BigInteger end) implements Comparable<FreshnessRange> {

    public boolean contains(BigInteger id) {
        return id.compareTo(start) >= 0 && id.compareTo(end) <= 0;
    }

    public BigInteger size() {
        return end.subtract(start).add(BigInteger.ONE);
    }

    public boolean overlapsOrAdjoins(FreshnessRange other) {
        BigInteger thisStart = this.start;
        BigInteger thisEnd = this.end.add(BigInteger.ONE);
        BigInteger otherStart = other.start;
        BigInteger otherEnd = other.end.add(BigInteger.ONE);

        return thisStart.compareTo(otherEnd) <= 0 && otherStart.compareTo(thisEnd) <= 0;
    }

    public FreshnessRange merge(FreshnessRange other) {
        BigInteger newStart = this.start.min(other.start);
        BigInteger newEnd = this.end.max(other.end);
        return new FreshnessRange(newStart, newEnd);
    }

    @Override
    public int compareTo(FreshnessRange o) {
        return this.start.compareTo(o.start);
    }
}
