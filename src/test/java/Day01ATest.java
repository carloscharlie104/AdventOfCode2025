import org.junit.jupiter.api.Test;
import software.aoc.day01.a.Dial;
import software.aoc.day01.a.Order;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day01ATest {

    @Test
    void testParseOrder() {
        Order order = new Order("R48");
        assertEquals('R', order.getDirection());
        assertEquals(48, order.getAmount());

        Order order2 = new Order("L5");
        assertEquals('L', order2.getDirection());
        assertEquals(5, order2.getAmount());
    }

    @Test
    void testDialRotation() {
        Dial dial = new Dial();
        // ERROR ANTERIOR: assertEquals(50, Dial.class);
        // CORRECCIÓN: Usar getCurrentPosition()
        assertEquals(50, dial.getCurrentPosition());

        dial.rotate(new Order("R10"));
        // ERROR ANTERIOR: assertEquals(60, dial.getClass());
        assertEquals(60, dial.getCurrentPosition());

        dial.rotate(new Order("L20"));
        // ERROR ANTERIOR: assertEquals(40, dial.getClass());
        assertEquals(40, dial.getCurrentPosition());
    }

    @Test
    void testDialWrapping() {
        Dial dial = new Dial();

        // R50 desde 50 -> 100 -> 0
        dial.rotate(new Order("R50"));
        assertEquals(0, dial.getCurrentPosition());

        // L1 desde 0 -> -1 -> 99
        dial.rotate(new Order("L1"));
        assertEquals(99, dial.getCurrentPosition());
    }

    @Test
    void testExampleScenario() {
        Dial dial = new Dial();
        String[] inputs = {
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        };

        int zeroCount = 0;

        for (String input : inputs) {
            dial.rotate(new Order(input));
            if (dial.getCurrentPosition() == 0) {
                zeroCount++;
            }
        }

        assertEquals(3, zeroCount);
    }

    @Test
    void solvePuzzle() throws java.io.IOException {
        Dial dial = new Dial();
        int zeroCount = 0;

        Path path = Path.of("src/test/resources/d01-a/orders.txt");

        if (!Files.exists(path)) {
            fail("No se encontró el archivo orders.txt en: " + path.toAbsolutePath());
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            if (!line.isBlank()) {
                dial.rotate(new Order(line.trim()));
                if (dial.getCurrentPosition() == 0) {
                    zeroCount++;
                }
            }
        }

        System.out.println("========================================");
        System.out.println("SOLUCIÓN FINAL DAY 01-A: " + zeroCount);
        System.out.println("========================================");
    }
}