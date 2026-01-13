package software.aoc.day12.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day12.a.service.PackingService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day12ATest {

    private final PackingService service = new PackingService();

    @Test
    @DisplayName("Example Scenario: 2 solvable regions")
    void testExampleScenario() {
        List<String> input = List.of(
                "0:", "###", "##.", "##.", "",
                "1:", "###", "##.", ".##", "",
                "2:", ".##", "###", "##.", "",
                "3:", "##.", "###", "##.", "",
                "4:", "###", "#..", "###", "",
                "5:", "###", ".#.", "###", "",
                "4x4: 0 0 0 0 2 0",
                "12x5: 1 0 1 0 2 2",
                "12x5: 1 0 1 0 3 2");

        long result = service.countValidRegions(input);
        assertEquals(2, result);
    }

    @Test
    @DisplayName("SOLVER: Day 12")
    void solveDay12() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day12/day12FinalTest.txt");

            long solution = service.countValidRegions(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 12-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error leyendo input: " + e.getMessage());
        }
    }
}