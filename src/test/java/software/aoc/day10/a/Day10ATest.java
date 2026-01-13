package software.aoc.day10.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day10.a.service.FactoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day10ATest {

    private final FactoryService service = new FactoryService();

    @Test
    @DisplayName("Example 1: [.##.] -> 2 presses")
    void testExample1() {
        String input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        assertEquals(2, service.calculateTotalMinPresses(List.of(input)));
    }

    @Test
    @DisplayName("Example 2: [...#.] -> 3 presses")
    void testExample2() {
        String input = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
        assertEquals(3, service.calculateTotalMinPresses(List.of(input)));
    }

    @Test
    @DisplayName("Example 3: [.###.#] -> 2 presses")
    void testExample3() {
        String input = "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}";
        assertEquals(2, service.calculateTotalMinPresses(List.of(input)));
    }

    @Test
    @DisplayName("SOLVER: Day 10")
    void solveDay10() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day10/day10FinalTest.txt");

            long solution = service.calculateTotalMinPresses(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 10-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
