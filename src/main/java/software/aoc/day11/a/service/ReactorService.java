package software.aoc.day11.a.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.aoc.day11.a.model.Network;
import software.aoc.day11.a.parser.NetworkParser;

public class ReactorService {

    private static final String START_NODE = "you";
    private static final String END_NODE = "out";

    private final NetworkParser parser;

    public ReactorService() {
        this.parser = new NetworkParser();
    }

    public BigInteger countPaths(List<String> rawInput) {
        Network network = parser.parse(rawInput);
        Map<String, BigInteger> memo = new HashMap<>();

        return countPathsRecursive(START_NODE, network, memo);
    }

    private BigInteger countPathsRecursive(String current, Network network, Map<String, BigInteger> memo) {
        if (current.equals(END_NODE)) {
            return BigInteger.ONE;
        }

        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        BigInteger totalPaths = BigInteger.ZERO;
        for (String neighbor : network.getOutputs(current)) {
            totalPaths = totalPaths.add(countPathsRecursive(neighbor, network, memo));
        }

        memo.put(current, totalPaths);
        return totalPaths;
    }
}
