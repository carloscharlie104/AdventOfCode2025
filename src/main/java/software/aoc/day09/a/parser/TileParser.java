package software.aoc.day09.a.parser;

import java.util.List;
import software.aoc.day09.a.model.Tile;

public class TileParser {

    public List<Tile> parse(List<String> rawInput) {
        return rawInput.stream()
                .filter(s -> !s.isBlank())
                .map(this::parseLine)
                .toList();
    }

    private Tile parseLine(String line) {
        String[] parts = line.split(",");
        return new Tile(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()));
    }
}
