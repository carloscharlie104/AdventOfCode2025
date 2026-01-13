package software.aoc.day06.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import software.aoc.Reader.InputReader;

import software.aoc.day06.a.service.TrashCompactorService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class Day06ATest {

    private final TrashCompactorService service = new TrashCompactorService();

    @Test
    @DisplayName("Example Scenario: Spatial parsing")
    void testExampleScenario() {
        List<String> inputWithDots = List.of(
                "123...328...51...64",
                ".45....64..387...23",
                "..6....98..215..314",
                "*.....+.....*....+.");

        List<String> input = inputWithDots.stream()
                .map(s -> s.replace('.', ' '))
                .toList();

        BigInteger result = service.calculateGrandTotal(input);
        assertEquals(new BigInteger("4277556"), result);
    }

    @Test
    @DisplayName("SOLVER: Day 06")
    void solveDay06() {
        InputReader reader = new InputReader();
        try {
            List<String> lines = reader.readLines("src/test/resources/day06/day06FinalTest.txt");

            BigInteger solution = service.calculateGrandTotal(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 06-A : " + solution);
            System.out.println("========================================");

        } catch (Exception e) {
            fail("Error reading input: " + e.getMessage());
        }
    }
}
