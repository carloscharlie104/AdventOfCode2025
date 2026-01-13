package software.aoc.day04.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day04.a.service.LogisticsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day04ATest {

    private final LogisticsService service = new LogisticsService();

    @Test
    @DisplayName("Example Scenario: Should find 13 accessible rolls")
    void testExampleScenario() {
        List<String> input = List.of(
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@.");

        long result = service.calculateSafeAccessPoints(input);
        assertEquals(13, result);
    }

    @Test
    @DisplayName("SOLVER: Day 04")
    void solveDay04() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day04/day04FinalTest.txt");

            long solution = service.calculateSafeAccessPoints(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 04-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
