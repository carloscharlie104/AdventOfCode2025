package software.aoc.day01.a.model;

public final class Dial {
    private static final int INITIAL_POSITION = 50;
    private static final int MAX_POSITION = 100;
    private static final int TARGET_POSITION = 0;

    private final int currentPosition;

    private Dial(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public static Dial createDefault() {
        return new Dial(INITIAL_POSITION);
    }

    public static Dial at(int position) {
        return new Dial(position);
    }

    public Dial rotate(Order order) {
        int newPosition = switch (order.direction()) {
            case RIGHT -> Math.floorMod(this.currentPosition + order.amount(), MAX_POSITION);
            case LEFT -> Math.floorMod(this.currentPosition - order.amount(), MAX_POSITION);
        };
        return new Dial(newPosition);
    }

    public boolean isAtTarget() {
        return this.currentPosition == TARGET_POSITION;
    }

    public int position() {
        return currentPosition;
    }
}
