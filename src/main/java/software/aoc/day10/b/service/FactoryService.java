package software.aoc.day10.b.service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import software.aoc.day10.b.model.Machine;
import software.aoc.day10.b.parser.MachineParser;

public class FactoryService {

    private final MachineParser parser;
    private final EquationSolver equationSolver;

    public FactoryService() {
        this.parser = new MachineParser();
        this.equationSolver = new EquationSolver();
    }

    public long calculateTotalMinPresses(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty())
            return 0;
        return rawLines.stream()
                .filter(line -> !line.isBlank())
                .map(parser::parse)
                .mapToLong(this::solveBfsPart1)
                .sum();
    }

    public long calculateTotalJoltagePresses(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty())
            return 0;
        return rawLines.stream()
                .filter(line -> !line.isBlank())
                .map(parser::parse)
                .mapToLong(equationSolver::solveMinPresses)
                .sum();
    }

    private int solveBfsPart1(Machine machine) {
        List<Integer> buttonMasks = machine.getButtonMasks();
        int target = machine.lightTargetState();

        int startState = 0;
        if (startState == target)
            return 0;

        Queue<State> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(new State(startState, 0));
        visited.add(startState);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            for (int mask : buttonMasks) {
                int nextState = current.val ^ mask;
                if (nextState == target)
                    return current.steps + 1;
                if (visited.add(nextState)) {
                    queue.add(new State(nextState, current.steps + 1));
                }
            }
        }
        throw new IllegalStateException("Unsolvable part 1");
    }

    private record State(int val, int steps) {
    }
}
