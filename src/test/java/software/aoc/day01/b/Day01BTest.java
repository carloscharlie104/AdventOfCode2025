package software.aoc.day01.b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import software.aoc.Reader.InputReader;
import software.aoc.day01.b.model.CountingStrategy;
import software.aoc.day01.b.model.Dial;
import software.aoc.day01.b.model.Direction;
import software.aoc.day01.b.model.Order;
import software.aoc.day01.b.service.SecurityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01BTest {

    @Test
    @DisplayName("Default dial should start at position 50")
    void defaultDialStart() {
        Dial dial = Dial.createDefault();
        assertEquals(50, dial.position());
    }

    @ParameterizedTest
    @DisplayName("Should count visits to target 0 during rotation")
    @CsvSource({
            "52, RIGHT, 48, 0, 1",
            "1, LEFT, 2, 99, 1",
            "2, LEFT, 2, 0, 1",
            "98, RIGHT, 2, 0, 1",
            "99, RIGHT, 2, 1, 1",
            "50, RIGHT, 100, 50, 1"
    })
    void shouldCountVisits(int startPos, Direction direction, int amount, int endPos, int expectedVisits) {
        Dial dial = Dial.at(startPos);
        Order order = new Order(direction, amount);

        int visits = dial.countVisitsDuringRotation(order);
        Dial next = dial.rotate(order);

        assertEquals(expectedVisits, visits, "Visits mismatch");
        assertEquals(endPos, next.position(), "End position mismatch");
    }

    @Test
    @DisplayName("Example Scenario: Should return 6 hits for problem example with continuous check")
    void testCrackPassword_WithExampleInput() {
        SecurityService service = new SecurityService();

        List<String> input = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82");

        int result = service.crackPassword(input, CountingStrategy.continuousCheck());

        assertEquals(6, result);
    }

    @Test
    @DisplayName("Integration: Should solve Part B with final input")
    void solvePuzzleDay01B() {
        SecurityService service = new SecurityService();
        InputReader reader = new InputReader();

        try {
            List<String> lines = reader.readLines("src/test/resources/day01/day01FinalTest.txt");
            int solution = service.crackPassword(lines, CountingStrategy.continuousCheck());

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 01-B : " + solution);
            System.out.println("========================================");

            assertEquals(6932, solution);

        } catch (Exception e) {
            System.out.println("Test skipped or failed: " + e.getMessage());
        }
    }
}
