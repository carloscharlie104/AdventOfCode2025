package software.aoc.day04.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day04.b.service.LogisticsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day04BTest {

    private final LogisticsService service = new LogisticsService();

    @Test
    @DisplayName("Part 2 Example: Iterative removal should total 43")
    void testExamplePart2() {
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

        long result = service.calculateTotalRemovableRolls(input);
        assertEquals(43, result);
    }

    @Test
    @DisplayName("SOLVER: Day 04 - Part 2")
    void solveDay04Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day04/day04FinalTest.txt");

            long solution = service.calculateTotalRemovableRolls(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 04-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
