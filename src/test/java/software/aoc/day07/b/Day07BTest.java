package software.aoc.day07.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day07.b.service.TeleporterService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day07BTest {

    private final TeleporterService service = new TeleporterService();

    @Test
    @DisplayName("Part 1 Example: 21 splits")
    void testExamplePart1() {
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
        assertEquals(21, service.countSplits(input));
    }

    @Test
    @DisplayName("Part 2 Example: 40 quantum timelines")
    void testExamplePart2() {
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
        BigInteger result = service.countQuantumTimelines(input);
        assertEquals(new BigInteger("40"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 07 - Part 2")
    void solveDay07Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day07/day07FinalTest.txt");

            BigInteger solution = service.countQuantumTimelines(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 07-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
