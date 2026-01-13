package software.aoc.day10.a.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import software.aoc.day10.a.model.Machine;

public class MachineParser {

    private static final Pattern LIGHTS_PATTERN = Pattern.compile("\\[([.#]+)\\]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([\\d,]+)\\)");

    public Machine parse(String line) {
        Matcher lightsMatcher = LIGHTS_PATTERN.matcher(line);
        if (!lightsMatcher.find()) {
            throw new IllegalArgumentException("No light diagram found in: " + line);
        }

        String lightsStr = lightsMatcher.group(1);
        int targetState = parseLights(lightsStr);

        List<Integer> buttons = new ArrayList<>();
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(line);
        while (buttonMatcher.find()) {
            String indicesStr = buttonMatcher.group(1);
            buttons.add(parseButtonMask(indicesStr));
        }

        return new Machine(targetState, buttons);
    }

    private int parseLights(String lights) {
        int state = 0;
        for (int i = 0; i < lights.length(); i++) {
            if (lights.charAt(i) == '#') {
                state |= (1 << i);
            }
        }
        return state;
    }

    private int parseButtonMask(String indicesStr) {
        int mask = 0;
        String[] parts = indicesStr.split(",");
        for (String part : parts) {
            int bitIndex = Integer.parseInt(part.trim());
            mask |= (1 << bitIndex);
        }
        return mask;
    }
}
