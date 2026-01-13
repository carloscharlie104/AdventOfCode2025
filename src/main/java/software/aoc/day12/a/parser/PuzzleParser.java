package software.aoc.day12.a.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.aoc.day12.a.model.PuzzleInput;
import software.aoc.day12.a.model.PuzzleTask;
import software.aoc.day12.a.model.Shape;

public class PuzzleParser {

    public PuzzleInput parse(List<String> lines) {
        Map<Integer, List<Shape>> shapeVariants = new HashMap<>();
        List<PuzzleTask> tasks = new ArrayList<>();
        List<String> buffer = new ArrayList<>();
        int currentId = -1;
        boolean parsingShapes = true;

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                flushShape(buffer, currentId, shapeVariants);
                continue;
            }
            if (line.contains("x") && line.contains(":")) {
                parsingShapes = false;
                flushShape(buffer, currentId, shapeVariants);
            }
            if (parsingShapes) {
                if (line.matches("\\d+:")) {
                    flushShape(buffer, currentId, shapeVariants);
                    currentId = Integer.parseInt(line.replace(":", "").trim());
                } else {
                    buffer.add(line);
                }
            } else {
                parseTaskLine(line, tasks);
            }
        }
        flushShape(buffer, currentId, shapeVariants);
        return new PuzzleInput(shapeVariants, tasks);
    }

    private void flushShape(List<String> buffer, int id, Map<Integer, List<Shape>> map) {
        if (!buffer.isEmpty() && id != -1) {
            Shape s = Shape.parse(id, new ArrayList<>(buffer));
            map.put(id, s.generateVariants());
            buffer.clear();
        }
    }

    private void parseTaskLine(String line, List<PuzzleTask> tasks) {
        try {
            String[] parts = line.split(":");
            String[] dims = parts[0].trim().split("x");
            int width = Integer.parseInt(dims[0]);
            int length = Integer.parseInt(dims[1]);
            int[] quantities = Arrays.stream(parts[1].trim().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();
            tasks.add(new PuzzleTask(width, length, quantities));
        } catch (Exception e) {

        }
    }
}
