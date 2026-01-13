package software.aoc.day04.a.parser;

import java.util.List;
import software.aoc.day04.a.model.Warehouse;

public class WarehouseParser {

    public Warehouse parse(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Grid cannot be empty");
        }
        int h = lines.size();
        int w = lines.get(0).length();
        boolean[][] newGrid = new boolean[h][w];

        for (int r = 0; r < h; r++) {
            String line = lines.get(r);
            if (line.length() != w)
                throw new IllegalArgumentException("Grid must be rectangular");
            for (int c = 0; c < w; c++) {
                newGrid[r][c] = line.charAt(c) == '@';
            }
        }
        return new Warehouse(newGrid, h, w);
    }
}
