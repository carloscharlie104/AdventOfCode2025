package software.aoc.day12.a.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import software.aoc.day12.a.model.Placement;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;

public class LoosePackingSolver implements SolverStrategy {

    private final PlacementGenerator generator;

    public LoosePackingSolver(PlacementGenerator generator) {
        this.generator = generator;
    }

    @Override
    public boolean solve(PuzzleTask task, Map<Integer, List<Shape>> shapeVariants) {
        List<List<Placement>> placementsByPiece = new ArrayList<>();
        int[] quantities = task.quantities();

        for (int id = 0; id < quantities.length; id++) {
            if (quantities[id] > 0) {
                List<Placement> forThisPiece = new ArrayList<>();
                for (Shape variant : shapeVariants.get(id)) {
                    forThisPiece.addAll(generator.generatePlacements(task, id, variant));
                }
                for (int k = 0; k < quantities[id]; k++) {
                    placementsByPiece.add(forThisPiece);
                }

            }
        }

        int gridSize = task.width() * task.length();
        BitSet board = new BitSet(gridSize);
        return search(0, board, placementsByPiece, gridSize);
    }

    private boolean search(int pieceIdx, BitSet board, List<List<Placement>> placementsByPiece, int gridSize) {
        if (pieceIdx == placementsByPiece.size()) {
            return true;
        }

        List<Placement> candidates = placementsByPiece.get(pieceIdx);
        for (Placement p : candidates) {
            BitSet intersection = (BitSet) board.clone();
            intersection.and(p.mask());
            if (intersection.isEmpty()) {
                board.or(p.mask());
                if (search(pieceIdx + 1, board, placementsByPiece, gridSize)) {
                    return true;
                }
                board.xor(p.mask());
            }
        }
        return false;
    }
}
