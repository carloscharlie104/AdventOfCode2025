package software.aoc.day07.b.model;

public class Manifold {
    private final char[][] grid;
    private final int width;
    private final int height;
    private final Beam startBeam;

    public Manifold(char[][] grid, int width, int height, Beam startBeam) {
        this.grid = grid;
        this.width = width;
        this.height = height;
        this.startBeam = startBeam;
    }

    public boolean isSplitter(int r, int c) {
        return isValid(r, c) && grid[r][c] == '^';
    }

    public boolean isInside(int r, int c) {
        return isValid(r, c);
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < height && c >= 0 && c < width;
    }

    public Beam getStartBeam() {
        return startBeam;
    }
}
