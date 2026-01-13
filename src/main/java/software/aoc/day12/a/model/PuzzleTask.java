package software.aoc.day12.a.model;

import java.util.Arrays;

public record PuzzleTask(int width, int length, int[] quantities) {
    @Override
    public String toString() {
        return width + "x" + length + ":" + Arrays.toString(quantities);
    }
}
