package software.aoc.day10.b.model;

import java.util.List;
import java.util.stream.Collectors;

public record Machine(
        int lightTargetState,
        List<List<Integer>> buttons,
        List<Integer> joltageTargets) {

    public List<Integer> getButtonMasks() {
        return buttons.stream()
                .map(indices -> {
                    int mask = 0;
                    for (int idx : indices)
                        mask |= (1 << idx);
                    return mask;
                })
                .collect(Collectors.toList());
    }
}
