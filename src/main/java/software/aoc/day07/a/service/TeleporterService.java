package software.aoc.day07.a.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import software.aoc.day07.a.model.Beam;
import software.aoc.day07.a.model.Manifold;
import software.aoc.day07.a.parser.ManifoldParser;

public class TeleporterService {

    private final ManifoldParser parser;

    public TeleporterService() {
        this.parser = new ManifoldParser();
    }

    public long countSplits(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;

        Manifold manifold = parser.parse(rawInput);
        Set<Beam> activeBeams = new HashSet<>();

        activeBeams.add(manifold.getStartBeam());

        long totalSplits = 0;

        while (!activeBeams.isEmpty()) {
            Set<Beam> nextStepBeams = new HashSet<>();

            for (Beam beam : activeBeams) {
                Beam nextPos = beam.moveDown();

                if (!manifold.isInside(nextPos.row(), nextPos.col())) {
                    continue;
                }

                if (manifold.isSplitter(nextPos.row(), nextPos.col())) {
                    totalSplits++;

                    Beam leftChild = nextPos.moveLeft();
                    Beam rightChild = nextPos.moveRight();

                    if (manifold.isInside(leftChild.row(), leftChild.col())) {
                        nextStepBeams.add(leftChild);
                    }
                    if (manifold.isInside(rightChild.row(), rightChild.col())) {
                        nextStepBeams.add(rightChild);
                    }
                } else {
                    nextStepBeams.add(nextPos);
                }
            }
            activeBeams = nextStepBeams;
        }

        return totalSplits;
    }
}
