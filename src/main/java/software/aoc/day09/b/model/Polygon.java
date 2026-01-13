package software.aoc.day09.b.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Segment> edges;

    public Polygon(List<Tile> vertices) {
        this.edges = new ArrayList<>();

        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Tile start = vertices.get(i);
            Tile end = vertices.get((i + 1) % n);
            edges.add(new Segment(start, end));
        }
    }

    public boolean containsRectangle(Tile t1, Tile t2) {
        long minX = Math.min(t1.x(), t2.x());
        long maxX = Math.max(t1.x(), t2.x());
        long minY = Math.min(t1.y(), t2.y());
        long maxY = Math.max(t1.y(), t2.y());

        for (Segment edge : edges) {
            if (edge.intersectsInterior(minX, maxX, minY, maxY)) {
                return false;
            }
        }

        double midX = (minX + maxX) / 2.0;
        double midY = (minY + maxY) / 2.0;

        return isPointInside(midX, midY);
    }

    private boolean isPointInside(double x, double y) {
        int intersections = 0;
        for (Segment edge : edges) {
            if (edge.isVertical()) {
                double segMinY = Math.min(edge.start().y(), edge.end().y());
                double segMaxY = Math.max(edge.start().y(), edge.end().y());
                double segX = edge.start().x();

                if (y > segMinY && y < segMaxY && x < segX) {
                    intersections++;
                }
            }
        }
        return (intersections % 2) != 0;
    }

    private record Segment(Tile start, Tile end) {

        boolean isVertical() {
            return start.x() == end.x();
        }

        boolean intersectsInterior(long minX, long maxX, long minY, long maxY) {
            if (isVertical()) {
                long x = start.x();
                long y1 = Math.min(start.y(), end.y());
                long y2 = Math.max(start.y(), end.y());

                boolean xInside = x > minX && x < maxX;
                boolean yOverlaps = Math.max(y1, minY) < Math.min(y2, maxY);

                return xInside && yOverlaps;
            } else {
                long y = start.y();
                long x1 = Math.min(start.x(), end.x());
                long x2 = Math.max(start.x(), end.x());

                boolean yInside = y > minY && y < maxY;
                boolean xOverlaps = Math.max(x1, minX) < Math.min(x2, maxX);

                return yInside && xOverlaps;
            }
        }
    }
}
