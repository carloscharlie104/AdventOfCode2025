package software.aoc.day01.b.parser;

import software.aoc.day01.b.model.Direction;
import software.aoc.day01.b.model.Order;

public class DialParser {

    public Order parse(String input) {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid input");
        }
        char dirChar = input.charAt(0);
        int amountVal = Integer.parseInt(input.substring(1));
        return new Order(dirChar == 'R' ? Direction.RIGHT : Direction.LEFT, amountVal);
    }
}
