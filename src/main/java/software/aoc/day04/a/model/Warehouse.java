package software.aoc.day04.a.model;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Warehouse {
    private final boolean[][] grid;
    private final int height;
    private final int width;

    private static final int[][] DIRECTIONS = {
            { -1, -1 }, { -1, 0 }, { -1, 1 },
            { 0, -1 }, { 0, 1 },
            { 1, -1 }, { 1, 0 }, { 1, 1 }
    };

    public Warehouse(boolean[][] grid, int height, int width) {
        this.grid = grid;
        this.height = height;
        this.width = width;
    }

    public long countAccessibleRolls() {
        return coordinates()
                .filter(this::isPaper)
                .filter(this::isAccessibleForForklift)
                .count();
    }

    private boolean isAccessibleForForklift(Coordinate coord) {
        long neighborCount = countPaperNeighbors(coord);
        return neighborCount < 4;
    }

    private long countPaperNeighbors(Coordinate coord) {
        int r = coord.row();
        int c = coord.col();
        int count = 0;

        for (int[] dir : DIRECTIONS) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (isValid(nr, nc) && grid[nr][nc]) {
                count++;
            }
        }
        return count;
    }

    private boolean isPaper(Coordinate coord) {
        return grid[coord.row()][coord.col()];
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < height && c >= 0 && c < width;
    }

    private Stream<Coordinate> coordinates() {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(r -> IntStream.range(0, width).mapToObj(c -> new Coordinate(r, c)));
    }
}
