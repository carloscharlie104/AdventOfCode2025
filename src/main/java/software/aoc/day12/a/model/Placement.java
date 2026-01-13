package software.aoc.day12.a.model;

import java.util.BitSet;

public record Placement(BitSet mask, int startBit, int pieceId, int cardinality) {
}
