package software.aoc.day01.b.model;

@FunctionalInterface
public interface CountingStrategy {

    int count(Dial currentDial, Order order);

    static CountingStrategy simpleCheck() {
        return (currentDial, order) -> {
            Dial nextDial = currentDial.rotate(order);
            return nextDial.isAtTarget() ? 1 : 0;
        };
    }

    static CountingStrategy continuousCheck() {
        return (currentDial, order) -> currentDial.countVisitsDuringRotation(order);
    }
}
