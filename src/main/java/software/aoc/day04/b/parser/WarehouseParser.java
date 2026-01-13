package software.aoc.day04.b.parser;

import java.util.List;
import software.aoc.day04.b.model.Warehouse;

public class WarehouseParser {

    public Warehouse parse(List<String> lines) {
        if (lines == null || lines.isEmpty())
            throw new IllegalArgumentException("Grid cannot be empty");
        int h = lines.size();
        int w = lines.get(0).length();
        boolean[][] newGrid = new boolean[h][w];

        for (int r = 0; r < h; r++) {
            String line = lines.get(r);
            for (int c = 0; c < w; c++) {
                newGrid[r][c] = line.charAt(c) == '@';
            }
        }
        return new Warehouse(newGrid, h, w);
    }
}
