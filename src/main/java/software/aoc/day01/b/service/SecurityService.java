package software.aoc.day01.b.service;

import java.util.List;
import software.aoc.day01.b.model.CountingStrategy;
import software.aoc.day01.b.model.Dial;

import software.aoc.day01.b.parser.DialParser;

public class SecurityService {

    private final DialParser parser = new DialParser();

    private record ProcessState(Dial dial, int totalCount) {
    }

    public int crackPassword(List<String> rawOrders, CountingStrategy strategy) {
        if (rawOrders == null || rawOrders.isEmpty()) {
            return 0;
        }

        ProcessState finalState = rawOrders.stream()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .map(parser::parse)
                .reduce(
                        new ProcessState(Dial.createDefault(), 0),
                        (state, order) -> {
                            int hits = strategy.count(state.dial(), order);
                            Dial nextDial = state.dial().rotate(order);
                            return new ProcessState(nextDial, state.totalCount() + hits);
                        },
                        (s1, s2) -> s1);

        return finalState.totalCount();
    }
}
