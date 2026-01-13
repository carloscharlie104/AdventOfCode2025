package software.aoc.day05.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day05.a.model.FreshnessRange;
import software.aoc.day05.a.parser.RangeParser;
import software.aoc.day05.a.service.CafeteriaService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day05ATest {

    private final CafeteriaService service = new CafeteriaService();

    @Test
    @DisplayName("Range Logic: Check containment")
    void testRangeContains() {
        FreshnessRange range = new RangeParser().parse("3-5");

        assertTrue(range.contains(new BigInteger("3")));
        assertTrue(range.contains(new BigInteger("4")));
        assertTrue(range.contains(new BigInteger("5")));

        assertFalse(range.contains(new BigInteger("2")));
        assertFalse(range.contains(new BigInteger("6")));
    }

    @Test
    @DisplayName("Example Scenario: Should count 3 fresh ingredients")
    void testExampleScenario() {
        List<String> input = List.of(
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "1",
                "5",
                "8",
                "11",
                "17",
                "32");

        long result = service.countFreshIngredients(input);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("SOLVER: Day 05")
    void solveDay05() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day05/day05FinalTest.txt");

            long solution = service.countFreshIngredients(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 05-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
