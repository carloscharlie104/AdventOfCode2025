package software.aoc.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputReader {
    public List<String> readLines(String filePath) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File not found: " + path.toAbsolutePath());
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading input file", e);
        }
    }
}