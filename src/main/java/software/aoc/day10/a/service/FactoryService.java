package software.aoc.day10.a.service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import software.aoc.day10.a.model.Machine;
import software.aoc.day10.a.parser.MachineParser;

public class FactoryService {

    private final MachineParser parser;

    public FactoryService() {
        this.parser = new MachineParser();
    }

    public long calculateTotalMinPresses(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty())
            return 0;

        return rawLines.stream()
                .filter(line -> !line.isBlank())
                .map(parser::parse)
                .mapToLong(this::solveMinimumPresses)
                .sum();
    }

    private int solveMinimumPresses(Machine machine) {
        int startState = 0;
        int target = machine.targetState();

        if (startState == target)
            return 0;

        Queue<State> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(new State(startState, 0));
        visited.add(startState);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            for (int buttonMask : machine.buttons()) {
                int nextState = current.val ^ buttonMask;

                if (nextState == target) {
                    return current.steps + 1;
                }

                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(new State(nextState, current.steps + 1));
                }
            }
        }

        throw new IllegalStateException("Unsolvable machine configuration: " + machine);
    }

    private record State(int val, int steps) {
    }
}
