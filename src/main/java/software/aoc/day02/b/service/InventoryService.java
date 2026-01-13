package software.aoc.day02.b.service;

import java.math.BigInteger;
import java.util.regex.Pattern;
import software.aoc.day02.b.model.ProductRange;
import software.aoc.day02.b.model.ValidationStrategy;
import software.aoc.day02.b.parser.RangeParser;

public class InventoryService {

    private final RangeParser parser = new RangeParser();

    public BigInteger calculateTotalInvalidIds(String rawInput, ValidationStrategy strategy) {
        if (rawInput == null || rawInput.isBlank()) {
            return BigInteger.ZERO;
        }

        return Pattern.compile(",")
                .splitAsStream(rawInput)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(parser::parse)
                .flatMap(ProductRange::stream)
                .filter(strategy::isInvalid)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
