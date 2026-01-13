package software.aoc.day09.b.service;

import java.util.List;
import software.aoc.day09.b.model.Polygon;
import software.aoc.day09.b.model.Tile;
import software.aoc.day09.b.parser.TileParser;

public class TheaterService {

    private final TileParser parser;

    public TheaterService() {
        this.parser = new TileParser();
    }

    public long findLargestRectangleArea(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;
        List<Tile> tiles = parser.parse(rawInput);
        long maxArea = 0;
        int n = tiles.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                maxArea = Math.max(maxArea, tiles.get(i).calculateAreaWith(tiles.get(j)));
            }
        }
        return maxArea;
    }

    public long findLargestValidRectangleArea(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;

        List<Tile> tiles = parser.parse(rawInput);

        Polygon polygon = new Polygon(tiles);

        long maxArea = 0;
        int n = tiles.size();

        for (int i = 0; i < n; i++) {
            Tile t1 = tiles.get(i);
            for (int j = i + 1; j < n; j++) {
                Tile t2 = tiles.get(j);

                long currentArea = t1.calculateAreaWith(t2);

                if (currentArea <= maxArea)
                    continue;

                if (polygon.containsRectangle(t1, t2)) {
                    maxArea = currentArea;
                }
            }
        }

        return maxArea;
    }
}
