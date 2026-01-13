package software.aoc.day11.b.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.aoc.day11.b.model.Network;

public class NetworkParser {

    public Network parse(List<String> lines) {
        Map<String, List<String>> map = new HashMap<>();

        for (String line : lines) {
            if (line.isBlank())
                continue;
            String[] parts = line.split(":");
            String source = parts[0].trim();
            String[] targets = parts[1].trim().split("\\s+");

            map.put(source, Arrays.asList(targets));
        }
        return new Network(map);
    }
}
