package software.aoc.day02.a.service;

import java.math.BigInteger;
import java.util.regex.Pattern;
import software.aoc.day02.a.model.ProductRange;
import software.aoc.day02.a.parser.RangeParser;

public class InventoryService {

    private final IdValidator validator;
    private final RangeParser parser = new RangeParser();

    public InventoryService(IdValidator validator) {
        this.validator = validator;
    }

    public BigInteger calculateTotalInvalidIds(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return BigInteger.ZERO;
        }

        return Pattern.compile(",")
                .splitAsStream(rawInput)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(parser::parse)
                .flatMap(ProductRange::stream)
                .filter(validator::isInvalidId)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
