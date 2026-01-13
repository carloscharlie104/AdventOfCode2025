package software.aoc.day11.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day11.b.service.ReactorService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day11BTest {

    private final ReactorService service = new ReactorService();

    @Test
    @DisplayName("Part 1 Example")
    void testExamplePart1() {
        List<String> input = List.of(
                "aaa: you hhh", "you: bbb ccc", "bbb: ddd eee", "ccc: ddd eee fff",
                "ddd: ggg", "eee: out", "fff: out", "ggg: out", "hhh: ccc fff iii", "iii: out");
        assertEquals(new BigInteger("5"), service.countPaths(input));
    }

    @Test
    @DisplayName("Part 2 Example: Must visit 'dac' and 'fft'")
    void testExamplePart2() {
        List<String> input = List.of(
                "svr: aaa bbb", "aaa: fft", "fft: ccc", "bbb: tty", "tty: ccc",
                "ccc: ddd eee", "ddd: hub", "hub: fff", "eee: dac", "dac: fff",
                "fff: ggg hhh", "ggg: out", "hhh: out");

        BigInteger result = service.countConstrainedPaths(input);
        assertEquals(new BigInteger("2"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 11 - Part 2")
    void solveDay11Part2() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day11/day11FinalTest.txt");

            BigInteger solution = service.countConstrainedPaths(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 11-B : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
