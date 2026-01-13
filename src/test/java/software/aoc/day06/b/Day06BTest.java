package software.aoc.day06.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day06.b.service.TrashCompactorService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day06BTest {

    private final TrashCompactorService service = new TrashCompactorService();

    @Test
    @DisplayName("Part 1: Horizontal Parsing with Dots Trick")
    void testExampleScenarioPart1() {
        List<String> inputWithDots = List.of(
                "123...328...51...64",
                ".45....64..387...23",
                "..6....98..215..314",
                "*.....+.....*....+.");

        List<String> input = inputWithDots.stream()
                .map(s -> s.replace('.', ' '))
                .toList();

        BigInteger result = service.calculateGrandTotal(input);
        assertEquals(new BigInteger("4277556"), result);
    }

    @Test
    @DisplayName("Part 2: Vertical Parsing Example")
    void testExampleScenarioPart2() {
        List<String> inputWithDots = List.of(
                "123...328...51...64",
                ".45...64...387...23",
                "..6...98...215..314",
                "*.....+.....*....+.");

        List<String> input = inputWithDots.stream()
                .map(s -> s.replace('.', ' '))
                .toList();

        BigInteger result = service.calculateGrandTotalPart2(input);
        assertEquals(new BigInteger("3263827"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 06 - Part 2")
    void solveDay06Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day06/day06FinalTest.txt");

            BigInteger solution = service.calculateGrandTotalPart2(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 06-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
