package software.aoc.day03.a.parser;

import java.util.List;
import software.aoc.day03.a.model.BatteryBank;

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
