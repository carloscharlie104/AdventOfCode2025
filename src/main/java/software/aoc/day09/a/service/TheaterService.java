package software.aoc.day09.a.service;

import java.util.List;
import software.aoc.day09.a.model.Tile;
import software.aoc.day09.a.parser.TileParser;

public class TheaterService {

    private final TileParser parser;

    public TheaterService() {
        this.parser = new TileParser();
    }

    public long findLargestRectangleArea(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty()) {
            return 0;
        }

        List<Tile> tiles = parser.parse(rawInput);

        long maxArea = 0;
        int n = tiles.size();

        for (int i = 0; i < n; i++) {
            Tile t1 = tiles.get(i);
            for (int j = i + 1; j < n; j++) {
                Tile t2 = tiles.get(j);

                long area = t1.calculateAreaWith(t2);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        return maxArea;
    }
}
