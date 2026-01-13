package software.aoc.day08.a.model;

public record Connection(int u, int v, long distanceSq) implements Comparable<Connection> {

    @Override
    public int compareTo(Connection other) {
        return Long.compare(this.distanceSq, other.distanceSq);
    }
}
