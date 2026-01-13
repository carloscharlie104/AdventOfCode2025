package software.aoc.day05.b.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import software.aoc.day05.b.model.FreshnessRange;
import software.aoc.day05.b.parser.RangeParser;

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

    public BigInteger countTotalUniqueFreshIds(List<String> rawLines) {
        if (rawLines == null || rawLines.isEmpty()) {
            return BigInteger.ZERO;
        }

        List<FreshnessRange> ranges = new ArrayList<>();
        for (String line : rawLines) {
            if (line.trim().isEmpty())
                break;
            ranges.add(parser.parse(line));
        }

        if (ranges.isEmpty())
            return BigInteger.ZERO;

        Collections.sort(ranges);

        List<FreshnessRange> mergedRanges = new ArrayList<>();
        FreshnessRange current = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            FreshnessRange next = ranges.get(i);

            if (current.overlapsOrAdjoins(next)) {
                current = current.merge(next);
            } else {
                mergedRanges.add(current);
                current = next;
            }
        }
        mergedRanges.add(current);

        return mergedRanges.stream()
                .map(FreshnessRange::size)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
