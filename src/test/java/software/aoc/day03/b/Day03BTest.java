package software.aoc.day03.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day03.b.model.JoltageStrategy;
import software.aoc.day03.b.service.EnergyService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day03BTest {

    private final EnergyService service = new EnergyService();

    @Test
    @DisplayName("Strategy Part 2: Example Scenarios (12 digits)")
    void testPart2Examples() {
        JoltageStrategy strategy = JoltageStrategy.pickTwelve();

        long res1 = strategy.calculateMaxJoltage(toList("987654321111111"));
        assertEquals(987654321111L, res1);

        long res2 = strategy.calculateMaxJoltage(toList("234234234234278"));
        assertEquals(434234234278L, res2);
    }

    @Test
    @DisplayName("Integration Part 2: Sum check")
    void testPart2Sum() {
        List<String> inputs = List.of(
                "987654321111111",
                "811111111111119",
                "234234234234278",
                "818181911112111");

        BigInteger result = service.calculateTotalOutput(inputs, JoltageStrategy.pickTwelve());
        assertEquals(new BigInteger("3121910778619"), result);
    }

    @Test
    @DisplayName("Regression: Part 1 should still work with Greedy Strategy")
    void testPart1Regression() {
        List<String> inputs = List.of(
                "987654321111111",
                "811111111111119");

        BigInteger result = service.calculateTotalOutput(inputs, JoltageStrategy.pickTwo());
        assertEquals(new BigInteger("187"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 03 - Part 2")
    void solveDay03Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day03/day03FinalTest.txt");

            BigInteger solution = service.calculateTotalOutput(lines, JoltageStrategy.pickTwelve());

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 03-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }

    private List<Integer> toList(String s) {
        return s.chars().map(Character::getNumericValue).boxed().toList();
    }
}
