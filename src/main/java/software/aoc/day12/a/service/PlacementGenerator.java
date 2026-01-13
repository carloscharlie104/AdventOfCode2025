package software.aoc.day12.a.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import software.aoc.day12.a.model.Placement;
import software.aoc.day12.a.model.Point;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;

public class PlacementGenerator {

    public List<Placement> generatePlacements(PuzzleTask task, int id, Shape shape) {
        List<Placement> placements = new ArrayList<>();
        int rows = task.length();
        int cols = task.width();
        int gridSize = rows * cols;
        int shapeH = shape.getHeight();
        int shapeW = shape.getWidth();

        for (int r = 0; r <= rows - shapeH; r++) {
            for (int c = 0; c <= cols - shapeW; c++) {
                BitSet mask = new BitSet(gridSize);
                boolean fits = true;
                int minIdx = Integer.MAX_VALUE;

                for (Point p : shape.getPoints()) {
                    int pr = r + p.r();
                    int pc = c + p.c();
                    if (pr >= rows || pc >= cols) {
                        fits = false;
                        break;
                    }
                    int idx = pr * cols + pc;
                    if (idx < minIdx)
                        minIdx = idx;
                    mask.set(idx);
                }

                if (fits) {
                    placements.add(new Placement(mask, minIdx, id, shape.getPoints().size()));
                }
            }
        }
        return placements;
    }
}
