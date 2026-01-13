package software.aoc.day11.b.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.aoc.day11.b.model.Network;
import software.aoc.day11.b.parser.NetworkParser;

public class ReactorService {

    private final NetworkParser parser;

    public ReactorService() {
        this.parser = new NetworkParser();
    }

    public BigInteger countPaths(List<String> rawInput) {
        Network network = parser.parse(rawInput);
        return countPathsBetween("you", "out", network);
    }

    public BigInteger countConstrainedPaths(List<String> rawInput) {
        Network network = parser.parse(rawInput);

        String start = "svr";
        String end = "out";
        String wp1 = "dac";
        String wp2 = "fft";

        BigInteger path1 = countPathsBetween(start, wp1, network)
                .multiply(countPathsBetween(wp1, wp2, network))
                .multiply(countPathsBetween(wp2, end, network));

        BigInteger path2 = countPathsBetween(start, wp2, network)
                .multiply(countPathsBetween(wp2, wp1, network))
                .multiply(countPathsBetween(wp1, end, network));

        return path1.add(path2);
    }

    private BigInteger countPathsBetween(String start, String target, Network network) {
        Map<String, BigInteger> memo = new HashMap<>();
        return countRecursive(start, target, network, memo);
    }

    private BigInteger countRecursive(String current, String target, Network network, Map<String, BigInteger> memo) {
        if (current.equals(target)) {
            return BigInteger.ONE;
        }
        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        BigInteger total = BigInteger.ZERO;

        for (String neighbor : network.getOutputs(current)) {
            total = total.add(countRecursive(neighbor, target, network, memo));
        }

        memo.put(current, total);
        return total;
    }
}
