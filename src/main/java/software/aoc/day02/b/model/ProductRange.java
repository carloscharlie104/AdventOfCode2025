package software.aoc.day02.b.model;

import java.math.BigInteger;
import java.util.stream.Stream;

public record ProductRange(BigInteger start, BigInteger end) {

    public Stream<BigInteger> stream() {
        BigInteger count = end.subtract(start).add(BigInteger.ONE);
        long size = count.longValueExact();
        return Stream.iterate(start, n -> n.add(BigInteger.ONE)).limit(size);
    }
}
