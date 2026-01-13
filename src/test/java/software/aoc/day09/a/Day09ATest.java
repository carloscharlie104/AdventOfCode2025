package software.aoc.day09.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day09.a.service.TheaterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day09ATest {

    private final TheaterService service = new TheaterService();

    @Test
    @DisplayName("Example Scenario: Max Area 50")
    void testExampleScenario() {
        List<String> input = List.of(
                "7,1", "11,1", "11,7", "9,7",
                "9,5", "2,5", "2,3", "7,3");

        long result = service.findLargestRectangleArea(input);
        assertEquals(50, result);
    }

    @Test
    @DisplayName("SOLVER: Day 09")
    void solveDay09() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day09/day09FinalTest.txt");

            long solution = service.findLargestRectangleArea(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 09-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
