package software.aoc.day12.a.service;

import java.util.List;
import java.util.Map;
import software.aoc.day12.a.model.PuzzleInput;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;
import software.aoc.day12.a.parser.PuzzleParser;

public class PackingService {

    private final PuzzleParser parser;
    private final SolverStrategy exactCoverSolver;
    private final SolverStrategy loosePackingSolver;

    public PackingService() {
        this.parser = new PuzzleParser();
        PlacementGenerator generator = new PlacementGenerator();
        this.exactCoverSolver = new ExactCoverSolver(generator);
        this.loosePackingSolver = new LoosePackingSolver(generator);
    }

    public long countValidRegions(List<String> rawInput) {
        PuzzleInput input = parser.parse(rawInput);

        return input.tasks().parallelStream()
                .filter(task -> solveTask(task, input.shapeVariants()))
                .count();
    }

    private boolean solveTask(PuzzleTask task, Map<Integer, List<Shape>> shapeVariants) {
        int[] inventory = task.quantities();
        int totalPieceArea = 0;

        for (int id = 0; id < inventory.length; id++) {
            if (inventory[id] > 0) {
                if (!shapeVariants.containsKey(id)) {
                    return false;
                }
                int pieceArea = shapeVariants.get(id).get(0).getPoints().size();
                totalPieceArea += pieceArea * inventory[id];
            }
        }

        int gridSize = task.width() * task.length();
        if (totalPieceArea > gridSize) {
            return false;
        }

        if (totalPieceArea == gridSize) {
            return exactCoverSolver.solve(task, shapeVariants);
        } else {
            return loosePackingSolver.solve(task, shapeVariants);
        }
    }
}
