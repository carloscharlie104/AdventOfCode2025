package software.aoc.day03.b.service;

import java.math.BigInteger;
import java.util.List;

import software.aoc.day03.b.model.JoltageStrategy;
import software.aoc.day03.b.parser.BatteryParser;

public class EnergyService {

    private final BatteryParser parser = new BatteryParser();

    public BigInteger calculateTotalOutput(List<String> rawBanks, JoltageStrategy strategy) {
        if (rawBanks == null || rawBanks.isEmpty()) {
            return BigInteger.ZERO;
        }

        return rawBanks.stream()
                .filter(line -> !line.isBlank())
                .map(String::trim)
                .map(parser::parse)
                .map(bank -> strategy.calculateMaxJoltage(bank.batteries()))
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
