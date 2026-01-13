package software.aoc.day08.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day08.a.service.PlaygroundService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day08ATest {

    private final PlaygroundService service = new PlaygroundService();

    @Test
    @DisplayName("Example Scenario: 10 connections -> Product 40")
    void testExampleScenario() {
        List<String> input = List.of(
                "162,817,812", "57,618,57", "906,360,560", "592,479,940", "352,342,300",
                "466,668,158", "542,29,236", "431,825,988", "739,650,466", "52,470,668",
                "216,146,977", "819,987,18", "117,168,530", "805,96,715", "346,949,466",
                "970,615,88", "941,993,340", "862,61,35", "984,92,344", "425,690,689");

        long result = service.calculateCircuitPower(input, 10);
        assertEquals(40, result);
    }

    @Test
    @DisplayName("SOLVER: Day 08")
    void solveDay08() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day08/day08FinalTest.txt");

            long solution = service.calculateCircuitPower(lines, 1000);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 08-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
