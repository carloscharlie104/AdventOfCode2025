package software.aoc.day03.b.model;

import java.util.List;

@FunctionalInterface
public interface JoltageStrategy {

    long calculateMaxJoltage(List<Integer> batteries);

    static JoltageStrategy pickTwo() {
        return new GreedySubsequenceStrategy(2);
    }

    static JoltageStrategy pickTwelve() {
        return new GreedySubsequenceStrategy(12);
    }
}

class GreedySubsequenceStrategy implements JoltageStrategy {
    private final int digitsToPick;

    public GreedySubsequenceStrategy(int digitsToPick) {
        this.digitsToPick = digitsToPick;
    }

    @Override
    public long calculateMaxJoltage(List<Integer> batteries) {
        int n = batteries.size();
        if (n < digitsToPick)
            return 0;

        long result = 0;
        int currentSearchStart = 0;

        for (int k = digitsToPick; k > 0; k--) {
            int searchEnd = n - k;

            int bestDigit = -1;
            int bestIndex = -1;

            for (int i = currentSearchStart; i <= searchEnd; i++) {
                int val = batteries.get(i);

                if (val == 9) {
                    bestDigit = 9;
                    bestIndex = i;
                    break;
                }

                if (val > bestDigit) {
                    bestDigit = val;
                    bestIndex = i;
                }
            }

            result = (result * 10) + bestDigit;
            currentSearchStart = bestIndex + 1;
        }

        return result;
    }
}
