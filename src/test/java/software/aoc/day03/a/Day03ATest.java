package software.aoc.day03.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day03.a.parser.BatteryParser;
import software.aoc.day03.a.service.EnergyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day03ATest {

    private final EnergyService service = new EnergyService();
    private final BatteryParser parser = new BatteryParser();

    @Test
    @DisplayName("Domain Logic: Examples from description")
    void testIndividualBanks() {
        assertEquals(98, parser.parse("987654321111111").calculateMaxJoltage());
        assertEquals(89, parser.parse("811111111111119").calculateMaxJoltage());
        assertEquals(78, parser.parse("234234234234278").calculateMaxJoltage());
        assertEquals(92, parser.parse("818181911112111").calculateMaxJoltage());
    }

    @Test
    @DisplayName("Service Integration: Sum of max joltages")
    void testServiceSum() {
        List<String> inputs = List.of(
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111");

        long result = service.calculateTotalOutput(inputs);

        assertEquals(357, result);
    }

    @Test
    @DisplayName("SOLVER: Day 03")
    void solveDay03() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day03/day03FinalTest.txt");
            long solution = service.calculateTotalOutput(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 03-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
