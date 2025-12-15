package software.aoc.day01.b;

/**
 * Representa una orden de rotación en el dial (Ej: R22, L50).
 */
public class Order {
    private final char direction; // 'L' o 'R'
    private final int steps;

    public Order(String instruction) {
        this.direction = instruction.charAt(0);
        this.steps = Integer.parseInt(instruction.substring(1));
    }

    public char getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }
}