package software.aoc.day07.b.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.aoc.day07.b.model.Beam;
import software.aoc.day07.b.model.Manifold;
import software.aoc.day07.b.parser.ManifoldParser;

public class TeleporterService {

    private final ManifoldParser parser;

    public TeleporterService() {
        this.parser = new ManifoldParser();
    }

    public long countSplits(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;
        Manifold manifold = parser.parse(rawInput);
        java.util.Set<Beam> activeBeams = new java.util.HashSet<>();
        activeBeams.add(manifold.getStartBeam());
        long totalSplits = 0;
        while (!activeBeams.isEmpty()) {
            java.util.Set<Beam> nextStepBeams = new java.util.HashSet<>();
            for (Beam beam : activeBeams) {
                Beam nextPos = beam.moveDown();
                if (!manifold.isInside(nextPos.row(), nextPos.col()))
                    continue;
                if (manifold.isSplitter(nextPos.row(), nextPos.col())) {
                    totalSplits++;
                    Beam l = nextPos.moveLeft();
                    Beam r = nextPos.moveRight();
                    if (manifold.isInside(l.row(), l.col()))
                        nextStepBeams.add(l);
                    if (manifold.isInside(r.row(), r.col()))
                        nextStepBeams.add(r);
                } else {
                    nextStepBeams.add(nextPos);
                }
            }
            activeBeams = nextStepBeams;
        }
        return totalSplits;
    }

    public BigInteger countQuantumTimelines(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return BigInteger.ZERO;

        Manifold manifold = parser.parse(rawInput);
        Beam start = manifold.getStartBeam();

        Map<Integer, BigInteger> activeTimelines = new HashMap<>();
        activeTimelines.put(start.col(), BigInteger.ONE);

        BigInteger totalFinishedTimelines = BigInteger.ZERO;
        int currentRow = start.row();

        while (!activeTimelines.isEmpty()) {
            Map<Integer, BigInteger> nextRowTimelines = new HashMap<>();
            int nextRow = currentRow + 1;

            for (Map.Entry<Integer, BigInteger> entry : activeTimelines.entrySet()) {
                int col = entry.getKey();
                BigInteger count = entry.getValue();

                if (!manifold.isInside(nextRow, col)) {
                    totalFinishedTimelines = totalFinishedTimelines.add(count);
                    continue;
                }

                if (manifold.isSplitter(nextRow, col)) {
                    if (manifold.isInside(nextRow, col - 1)) {
                        nextRowTimelines.merge(col - 1, count, BigInteger::add);
                    } else {
                        totalFinishedTimelines = totalFinishedTimelines.add(count);
                    }

                    if (manifold.isInside(nextRow, col + 1)) {
                        nextRowTimelines.merge(col + 1, count, BigInteger::add);
                    } else {
                        totalFinishedTimelines = totalFinishedTimelines.add(count);
                    }
                } else {
                    nextRowTimelines.merge(col, count, BigInteger::add);
                }
            }

            activeTimelines = nextRowTimelines;
            currentRow++;
        }

        return totalFinishedTimelines;
    }
}
