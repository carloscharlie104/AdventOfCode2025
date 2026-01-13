package software.aoc.day12.a.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import software.aoc.day12.a.model.Placement;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;

public class ExactCoverSolver implements SolverStrategy {

    private final PlacementGenerator generator;

    public ExactCoverSolver(PlacementGenerator generator) {
        this.generator = generator;
    }

    @Override
    public boolean solve(PuzzleTask task, Map<Integer, List<Shape>> shapeVariants) {
        List<Placement> allPlacements = new ArrayList<>();
        int[] quantities = task.quantities();

        for (int id = 0; id < quantities.length; id++) {
            if (quantities[id] == 0)
                continue;
            for (Shape variant : shapeVariants.get(id)) {
                allPlacements.addAll(generator.generatePlacements(task, id, variant));
            }
        }
        int gridSize = task.width() * task.length();
        BitSet board = new BitSet(gridSize);
        return search(0, board, allPlacements, quantities, gridSize);
    }

    private boolean search(int cellIdx, BitSet board, List<Placement> placements, int[] quantities, int gridSize) {
        if (board.cardinality() == gridSize) {
            return true;
        }

        int nextCell = board.nextClearBit(cellIdx);
        if (nextCell >= gridSize) {
            return false;
        }

        for (Placement p : placements) {
            if (p.startBit() <= nextCell && p.mask().get(nextCell)) {
                if (quantities[p.pieceId()] > 0) {
                    BitSet intersection = (BitSet) board.clone();
                    intersection.and(p.mask());
                    if (intersection.isEmpty()) {

                        board.or(p.mask());
                        quantities[p.pieceId()]--;

                        if (search(nextCell + 1, board, placements, quantities, gridSize)) {
                            return true;
                        }

                        quantities[p.pieceId()]++;
                        board.xor(p.mask());
                    }
                }
            }
        }
        return false;
    }
}
