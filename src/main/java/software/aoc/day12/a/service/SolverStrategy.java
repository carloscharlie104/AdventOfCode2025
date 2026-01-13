package software.aoc.day12.a.service;

import java.util.List;
import java.util.Map;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;

public interface SolverStrategy {
    boolean solve(PuzzleTask task, Map<Integer, List<Shape>> shapeVariants);
}
