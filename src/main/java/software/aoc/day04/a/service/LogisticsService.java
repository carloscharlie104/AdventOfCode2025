package software.aoc.day04.a.service;

import java.util.List;
import software.aoc.day04.a.model.Warehouse;
import software.aoc.day04.a.parser.WarehouseParser;

public class LogisticsService {

    private final WarehouseParser parser = new WarehouseParser();

    public long calculateSafeAccessPoints(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty()) {
            return 0;
        }

        Warehouse warehouse = parser.parse(rawInput);
        return warehouse.countAccessibleRolls();
    }
}
