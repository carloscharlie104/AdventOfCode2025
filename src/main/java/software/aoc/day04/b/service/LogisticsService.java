package software.aoc.day04.b.service;

import java.util.List;
import software.aoc.day04.b.model.Coordinate;
import software.aoc.day04.b.model.Warehouse;
import software.aoc.day04.b.parser.WarehouseParser;

public class LogisticsService {

    private final WarehouseParser parser = new WarehouseParser();

    public long calculateSafeAccessPoints(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;
        return parser.parse(rawInput).countAccessibleRolls();
    }

    public long calculateTotalRemovableRolls(List<String> rawInput) {
        if (rawInput == null || rawInput.isEmpty())
            return 0;

        Warehouse currentWarehouse = parser.parse(rawInput);
        long totalRemoved = 0;

        while (true) {
            List<Coordinate> toRemove = currentWarehouse.findAccessibleCoordinates();

            if (toRemove.isEmpty()) {
                break;
            }

            totalRemoved += toRemove.size();

            currentWarehouse = currentWarehouse.removeRolls(toRemove);
        }

        return totalRemoved;
    }
}
