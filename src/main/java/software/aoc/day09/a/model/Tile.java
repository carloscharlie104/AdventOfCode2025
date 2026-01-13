package software.aoc.day09.a.model;

public record Tile(int x, int y) {

    public long calculateAreaWith(Tile other) {
        long width = Math.abs(this.x - other.x) + 1;
        long height = Math.abs(this.y - other.y) + 1;
        return width * height;
    }
}
