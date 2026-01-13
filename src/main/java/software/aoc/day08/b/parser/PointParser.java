package software.aoc.day08.b.parser;

import java.util.List;
import software.aoc.day08.b.model.Point3D;

public class PointParser {

    public List<Point3D> parse(List<String> rawInput) {
        return rawInput.stream()
                .filter(s -> !s.isBlank())
                .map(String::trim)
                .map(this::parseLine)
                .toList();
    }

    private Point3D parseLine(String line) {
        String[] parts = line.split(",");
        return new Point3D(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]));
    }
}
