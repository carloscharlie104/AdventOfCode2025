package software.aoc.day02.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;
import software.aoc.day02.b.model.ValidationStrategy;
import software.aoc.day02.b.service.InventoryService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day02BTest {

    private final InventoryService service = new InventoryService();

    @Test
    @DisplayName("Strategy Part 2 Logic: Repeating patterns")
    void testPart2StrategyLogic() {
        ValidationStrategy strategy = ValidationStrategy.atLeastTwice();

        assertTrue(strategy.isInvalid(new BigInteger("12341234")));
        assertTrue(strategy.isInvalid(new BigInteger("123123123")));
        assertTrue(strategy.isInvalid(new BigInteger("1212121212")));
        assertTrue(strategy.isInvalid(new BigInteger("1111111")));

        assertFalse(strategy.isInvalid(new BigInteger("121")));
        assertFalse(strategy.isInvalid(new BigInteger("10101")));
    }

    @Test
    @DisplayName("Integration Part 2: Example Scenario")
    void testExampleScenarioPart2() {
        String exampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124";

        BigInteger result = service.calculateTotalInvalidIds(exampleInput, ValidationStrategy.atLeastTwice());

        assertEquals(new BigInteger("4174379265"), result);
    }

    @Test
    @DisplayName("Regression: Part 1 Logic should still work via Strategy")
    void testPart1Regression() {
        String exampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                "824824821-824824827,2121212118-2121212124";

        BigInteger result = service.calculateTotalInvalidIds(exampleInput, ValidationStrategy.exactTwice());
        assertEquals(new BigInteger("1227775554"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 02 - Part 2")
    void solvePuzzlePart2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day02/day02FinalTest.txt");
            String fullInput = String.join(",", lines);

            BigInteger solution = service.calculateTotalInvalidIds(fullInput, ValidationStrategy.atLeastTwice());

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 02-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
