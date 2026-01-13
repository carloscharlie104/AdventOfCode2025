package software.aoc.day07.b.model;

/**
 * Representa un rayo de taquiones en una posición específica.
 * Inmutable.
 */
public record Beam(int row, int col) {

    public Beam moveDown() {
        return new Beam(row + 1, col);
    }

    public Beam moveLeft() {
        return new Beam(row, col - 1);
    }

    public Beam moveRight() {
        return new Beam(row, col + 1);
    }
}
