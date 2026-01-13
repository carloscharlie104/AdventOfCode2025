package software.aoc.day03.a.service;

import java.util.List;
import software.aoc.day03.a.model.BatteryBank;
import software.aoc.day03.a.parser.BatteryParser;

public class EnergyService {

    private final BatteryParser parser = new BatteryParser();

    public long calculateTotalOutput(List<String> rawBanks) {
        if (rawBanks == null || rawBanks.isEmpty()) {
            return 0;
        }

        return rawBanks.stream()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .map(parser::parse)
                .mapToInt(BatteryBank::calculateMaxJoltage)
                .sum();
    }
}
