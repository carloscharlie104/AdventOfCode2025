package software.aoc.day01.b.model;

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
        int newPosition = calculateEndPosition(this.currentPosition, order);
        return new Dial(newPosition);
    }

    public int countVisitsDuringRotation(Order order) {
        int visits = 0;
        int tempPos = this.currentPosition;

        for (int i = 0; i < order.amount(); i++) {
            if (order.direction() == Direction.RIGHT) {
                tempPos = (tempPos + 1) % MAX_POSITION;
            } else {
                tempPos = (tempPos - 1);
                if (tempPos < 0)
                    tempPos += MAX_POSITION;
            }

            if (tempPos == TARGET_POSITION) {
                visits++;
            }
        }
        return visits;
    }

    public boolean isAtTarget() {
        return this.currentPosition == TARGET_POSITION;
    }

    private int calculateEndPosition(int start, Order order) {
        return switch (order.direction()) {
            case RIGHT -> Math.floorMod(start + order.amount(), MAX_POSITION);
            case LEFT -> Math.floorMod(start - order.amount(), MAX_POSITION);
        };
    }

    public int position() {
        return currentPosition;
    }
}
