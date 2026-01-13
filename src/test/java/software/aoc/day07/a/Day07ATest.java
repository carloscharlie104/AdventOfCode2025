package software.aoc.day07.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day07.a.service.TeleporterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day07ATest {

    private final TeleporterService service = new TeleporterService();

    @Test
    @DisplayName("Example Scenario: 21 splits")
    void testExampleScenario() {
        List<String> input = List.of(
                ".......S.......",
                "...............",
                ".......^.......",
                "...............",
                "......^.^......",
                "...............",
                ".....^.^.^.....",
                "...............",
                "....^.^...^....",
                "...............",
                "...^.^...^.^...",
                "...............",
                "..^...^.....^..",
                "...............",
                ".^.^.^.^.^...^.",
                "...............");

        long result = service.countSplits(input);
        assertEquals(21, result);
    }

    @Test
    @DisplayName("SOLVER: Day 07")
    void solveDay07() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day07/day07FinalTest.txt");

            long solution = service.countSplits(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 07-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
