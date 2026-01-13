package software.aoc.day02.b.model;

import java.math.BigInteger;

@FunctionalInterface
public interface ValidationStrategy {

    boolean isInvalid(BigInteger id);

    static ValidationStrategy exactTwice() {
        return id -> {
            String s = id.toString();
            int len = s.length();
            if (len % 2 != 0)
                return false;

            String part1 = s.substring(0, len / 2);
            String part2 = s.substring(len / 2);
            return part1.equals(part2);
        };
    }

    static ValidationStrategy atLeastTwice() {
        return id -> {
            String s = id.toString();
            int len = s.length();

            for (int patternLen = 1; patternLen <= len / 2; patternLen++) {

                if (len % patternLen != 0)
                    continue;

                String pattern = s.substring(0, patternLen);
                int repeats = len / patternLen;

                String reconstructed = pattern.repeat(repeats);

                if (s.equals(reconstructed)) {
                    return true;
                }
            }
            return false;
        };
    }
}
