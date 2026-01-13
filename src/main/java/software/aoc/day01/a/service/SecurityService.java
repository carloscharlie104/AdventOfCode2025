package software.aoc.day01.a.service;

import java.util.List;
import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Order;
import software.aoc.day01.a.parser.DialParser;

public class SecurityService {

    private final DialParser parser = new DialParser();

    private record DialState(Dial dial, int zeroCount) {
        public DialState next(Order order) {
            Dial nextDial = dial.rotate(order);
            int hit = nextDial.isAtTarget() ? 1 : 0;
            return new DialState(nextDial, zeroCount + hit);
        }
    }

    public int crackPassword(List<String> rawOrders) {
        if (rawOrders == null || rawOrders.isEmpty()) {
            return 0;
        }

        DialState finalState = rawOrders.stream()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .map(parser::parse)
                .reduce(
                        new DialState(Dial.createDefault(), 0),
                        DialState::next,
                        (s1, s2) -> s1);

        return finalState.zeroCount();
    }
}
