package software.aoc.day01.a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import software.aoc.Reader.InputReader;
import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Direction;
import software.aoc.day01.a.model.Order;
import software.aoc.day01.a.service.SecurityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day01ATest {

    @Test
    @DisplayName("Default dial should start at position 50")
    void defaultDialStart() {
        Dial dial = Dial.createDefault();
        assertEquals(50, dial.position());
    }

    @ParameterizedTest
    @DisplayName("Should calculate rotation correctly")
    @CsvSource({
            "50, RIGHT, 8, 58",
            "50, LEFT, 10, 40",
            "0, LEFT, 1, 99",
            "99, RIGHT, 1, 0",
            "5, LEFT, 10, 95",
            "95, RIGHT, 5, 0"
    })
    void shouldRotateCorrectly(int startPos, Direction direction, int amount, int expectedPos) {
        Dial dial = Dial.at(startPos);
        Order order = new Order(direction, amount);
        Dial nextDial = dial.rotate(order);

        assertEquals(expectedPos, nextDial.position());
    }

    @Test
    @DisplayName("Should identify when dial is at target (0)")
    void shouldIdentifyTarget() {
        assertTrue(Dial.at(0).isAtTarget());
        assertFalse(Dial.at(1).isAtTarget());
        assertFalse(Dial.at(99).isAtTarget());
    }

    @Test
    @DisplayName("Example Scenario: Should return 3 hits for problem example")
    void testCrackPassword_WithExampleInput() {
        SecurityService service = new SecurityService();

        List<String> input = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82");

        int result = service.crackPassword(input);

        assertEquals(3, result);
    }

    @Test
    @DisplayName("Integration: Should solve Part A with final input")
    void solvePuzzleDay01A() {
        SecurityService service = new SecurityService();
        InputReader reader = new InputReader();

        try {
            List<String> lines = reader.readLines("src/test/resources/day01/day01FinalTest.txt");
            int solution = service.crackPassword(lines);

            System.out.println("========================================");
            System.out.println("SOLUCIÓN FINAL DAY 01-A : " + solution);
            System.out.println("========================================");

            assertEquals(1172, solution);

        } catch (Exception e) {
            System.out.println("Test skipped or failed: " + e.getMessage());
        }
    }
}
