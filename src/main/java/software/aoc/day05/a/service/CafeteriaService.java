package software.aoc.day05.a.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import software.aoc.day05.a.model.FreshnessRange;
import software.aoc.day05.a.parser.RangeParser;

public class CafeteriaService {

    private final RangeParser parser = new RangeParser();

    public long countFreshIngredients(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty()) {
            return 0;
        }

        List<FreshnessRange> ranges = new ArrayList<>();
        List<BigInteger> availableIds = new ArrayList<>();
        boolean parsingRanges = true;

        for (String line : rawLines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) {
                parsingRanges = false;
                continue;
            }

            if (parsingRanges) {
                ranges.add(parser.parse(trimmed));
            } else {
                availableIds.add(new BigInteger(trimmed));
            }
        }

        return availableIds.stream()
                .filter(id -> isFresh(id, ranges))
                .count();
    }

    private boolean isFresh(BigInteger id, List<FreshnessRange> ranges) {
        return ranges.stream()
                .anyMatch(range -> range.contains(id));
    }
}
