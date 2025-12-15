package software.aoc.day01.a;

public class Order {
    private final char direction;
    private final int amount;

    public Order(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid order format: " + input);
        }
        this.direction = input.charAt(0);
        this.amount = Integer.parseInt(input.substring(1));
    }

    public char getDirection() {
        return direction;
    }

    public int getAmount() {
        return amount;
    }
}