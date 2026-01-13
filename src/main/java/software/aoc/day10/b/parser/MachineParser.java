package software.aoc.day10.b.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import software.aoc.day10.b.model.Machine;

public class MachineParser {

    private static final Pattern LIGHTS_PATTERN = Pattern.compile("\\[([.#]+)\\]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([\\d,]+)\\)");
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([\\d,]+)\\}");

    public Machine parse(String line) {
        Matcher lightsMatcher = LIGHTS_PATTERN.matcher(line);
        int lightTarget = 0;
        if (lightsMatcher.find()) {
            lightTarget = parseLights(lightsMatcher.group(1));
        }

        List<List<Integer>> buttons = new ArrayList<>();
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(line);
        while (buttonMatcher.find()) {
            String indicesStr = buttonMatcher.group(1);
            buttons.add(parseIndices(indicesStr));
        }

        List<Integer> joltageTargets = new ArrayList<>();
        Matcher joltageMatcher = JOLTAGE_PATTERN.matcher(line);
        if (joltageMatcher.find()) {
            joltageTargets = parseIndices(joltageMatcher.group(1));
        }

        return new Machine(lightTarget, buttons, joltageTargets);
    }

    private int parseLights(String lights) {
        int state = 0;
        for (int i = 0; i < lights.length(); i++) {
            if (lights.charAt(i) == '#')
                state |= (1 << i);
        }
        return state;
    }

    private List<Integer> parseIndices(String str) {
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
