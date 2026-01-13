package software.aoc.day03.b.parser;

import java.util.List;
import software.aoc.day03.b.model.BatteryBank;

public class BatteryParser {

    public BatteryBank parse(String rawLine) {
        if (rawLine == null || rawLine.isBlank()) {
            throw new IllegalArgumentException("Battery bank input cannot be empty");
        }

        List<Integer> parsedBatteries = rawLine.chars()
                .map(Character::getNumericValue)
                .boxed()
                .toList();

        return new BatteryBank(parsedBatteries);
    }
}
