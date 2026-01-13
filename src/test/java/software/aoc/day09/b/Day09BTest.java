package software.aoc.day09.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day09.b.service.TheaterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day09BTest {

    private final TheaterService service = new TheaterService();

    @Test
    @DisplayName("Part 1 Example")
    void testExamplePart1() {
        List<String> input = List.of(
                "7,1", "11,1", "11,7", "9,7",
                "9,5", "2,5", "2,3", "7,3");
        assertEquals(50, service.findLargestRectangleArea(input));
    }

    @Test
    @DisplayName("Part 2 Example: Valid Green Area")
    void testExamplePart2() {
        List<String> input = List.of(
                "7,1", "11,1", "11,7", "9,7",
                "9,5", "2,5", "2,3", "7,3");
        assertEquals(24, service.findLargestValidRectangleArea(input));
    }

    @Test
    @DisplayName("SOLVER: Day 09 - Part 2")
    void solveDay09Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day09/day09FinalTest.txt");

            long solution = service.findLargestValidRectangleArea(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 09-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
