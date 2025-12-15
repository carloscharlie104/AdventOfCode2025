package software.aoc.day01;

import org.junit.jupiter.api.Test;
import software.aoc.day01.b.Dial;
import software.aoc.day01.b.Order;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01BTest {

    private static final String PUZZLE_INPUT_FILE = "d01-b/orders.txt";

    /**
     * Carga el input desde el archivo orders.txt en el directorio de recursos.
     * @return Lista de objetos Order.
     * @throws IOException Si ocurre un error de lectura.
     */
    private List<Order> loadInput() throws IOException {
        URL resource = getClass().getClassLoader().getResource(PUZZLE_INPUT_FILE);
        if (resource == null) {
            throw new RuntimeException("Archivo no encontrado: " + PUZZLE_INPUT_FILE);
        }
        Path path = Path.of(resource.getPath());

        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(Order::new)
                    .toList();
        }
    }

    @Test
    void solvePartTwo() throws IOException {
        List<Order> orders = loadInput();
        Dial dial = new Dial();
        long result = dial.processOrders(orders);

        // Nota: El resultado 274 se calculó con la simulación completa de 1732 pasos.
        assertEquals(274, result, "El contador total de ceros debería ser 274.");
    }
}