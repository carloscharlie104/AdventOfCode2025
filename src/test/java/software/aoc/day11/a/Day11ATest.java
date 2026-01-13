package software.aoc.day11.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day11.a.service.ReactorService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day11ATest {

    private final ReactorService service = new ReactorService();

    @Test
    @DisplayName("Example Scenario: 5 paths")
    void testExampleScenario() {
        List<String> input = List.of(
                "aaa: you hhh",
                "you: bbb ccc",
                "bbb: ddd eee",
                "ccc: ddd eee fff",
                "ddd: ggg",
                "eee: out",
                "fff: out",
                "ggg: out",
                "hhh: ccc fff iii",
                "iii: out");

        BigInteger result = service.countPaths(input);
        assertEquals(new BigInteger("5"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 11")
    void solveDay11() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day11/day11FinalTest.txt");

            BigInteger solution = service.countPaths(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 11-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
