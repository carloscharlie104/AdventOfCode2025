package software.aoc.day01.a.parser;

import software.aoc.day01.a.model.Direction;
import software.aoc.day01.a.model.Order;

public class DialParser {

    public Order parse(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid order format: " + input);
        }
        char dirChar = input.charAt(0);
        int amountVal = Integer.parseInt(input.substring(1));

        Direction dir = (dirChar == 'R') ? Direction.RIGHT : Direction.LEFT;
        return new Order(dir, amountVal);
    }
}
