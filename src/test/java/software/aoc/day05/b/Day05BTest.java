package software.aoc.day05.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day05.b.service.CafeteriaService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day05BTest {

    private final CafeteriaService service = new CafeteriaService();

    @Test
    @DisplayName("Part 2 Example: Merge overlapping ranges")
    void testPart2Example() {
        List<String> input = List.of(
                "3-5",
                "10-14",
                "16-20",
                "12-18",
                "",
                "IGNORE THIS PART");

        BigInteger result = service.countTotalUniqueFreshIds(input);
        assertEquals(new BigInteger("14"), result);
    }

    @Test
    @DisplayName("Logic: Should merge adjacent ranges")
    void testAdjacentMerge() {
        List<String> input = List.of("1-5", "6-10");
        BigInteger result = service.countTotalUniqueFreshIds(input);
        assertEquals(new BigInteger("10"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 05 - Part 2")
    void solveDay05Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day05/day05FinalTest.txt");

            BigInteger solution = service.countTotalUniqueFreshIds(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 05-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
