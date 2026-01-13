package software.aoc.day12.a.model;

import java.util.List;
import java.util.Map;

public record PuzzleInput(Map<Integer, List<Shape>> shapeVariants, List<PuzzleTask> tasks) {
}
