package software.aoc.day11.b.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Network {
    private final Map<String, List<String>> adjacencyList;

    public Network(Map<String, List<String>> adjacencyList) {
        this.adjacencyList = Map.copyOf(adjacencyList);
    }

    public List<String> getOutputs(String device) {
        return adjacencyList.getOrDefault(device, Collections.emptyList());
    }
}
