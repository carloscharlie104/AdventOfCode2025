package software.aoc.day10.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day10.b.service.FactoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day10BTest {

    private final FactoryService service = new FactoryService();

    @Test
    @DisplayName("Part 1 Regression")
    void testPart1() {
        String input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        assertEquals(2, service.calculateTotalMinPresses(List.of(input)));
    }

    @Test
    @DisplayName("Part 2 Example 1: 10 presses")
    void testPart2Ex1() {
        String input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        assertEquals(10, service.calculateTotalJoltagePresses(List.of(input)));
    }

    @Test
    @DisplayName("Part 2 Example 2: 12 presses")
    void testPart2Ex2() {
        String input = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
        assertEquals(12, service.calculateTotalJoltagePresses(List.of(input)));
    }

    @Test
    @DisplayName("SOLVER: Day 10 - Part 2")
    void solveDay10Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day10/day10FinalTest.txt");

            long solution = service.calculateTotalJoltagePresses(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 10-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
