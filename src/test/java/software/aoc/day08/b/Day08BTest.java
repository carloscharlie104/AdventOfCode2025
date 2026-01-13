package software.aoc.day08.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day08.b.service.PlaygroundService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day08BTest {

    private final PlaygroundService service = new PlaygroundService();

    @Test
    @DisplayName("Part 2 Example: Last connection product")
    void testPart2Example() {
        List<String> input = List.of(
                "162,817,812", "57,618,57", "906,360,560", "592,479,940", "352,342,300",
                "466,668,158", "542,29,236", "431,825,988", "739,650,466", "52,470,668",
                "216,146,977", "819,987,18", "117,168,530", "805,96,715", "346,949,466",
                "970,615,88", "941,993,340", "862,61,35", "984,92,344", "425,690,689");

        long result = service.findLastConnectionCoordinatesProduct(input);
        assertEquals(25272, result);
    }

    @Test
    @DisplayName("SOLVER: Day 08 - Part 2")
    void solveDay08Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day08/day08FinalTest.txt");

            long solution = service.findLastConnectionCoordinatesProduct(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 08-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
