package software.aoc.day12.a.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Shape {
    private final int id;
    private final Set<Point> points;
    private final int height;
    private final int width;

    public Shape(int id, Set<Point> points) {
        this.id = id;
        int minR = points.stream().mapToInt(Point::r).min().orElse(0);
        int minC = points.stream().mapToInt(Point::c).min().orElse(0);

        this.points = points.stream()
                .map(p -> new Point(p.r() - minR, p.c() - minC))
                .collect(Collectors.toSet());

        this.height = this.points.stream().mapToInt(Point::r).max().orElse(0) + 1;
        this.width = this.points.stream().mapToInt(Point::c).max().orElse(0) + 1;
    }

    public static Shape parse(int id, List<String> lines) {
        Set<Point> pts = new HashSet<>();
        for (int r = 0; r < lines.size(); r++) {
            String line = lines.get(r);
            for (int c = 0; c < line.length(); c++) {
                if (line.charAt(c) == '#') {
                    pts.add(new Point(r, c));
                }
            }
        }
        return new Shape(id, pts);
    }

    public List<Shape> generateVariants() {
        Set<Set<Point>> seen = new HashSet<>();
        List<Shape> variants = new ArrayList<>();

        Shape current = this;
        for (int i = 0; i < 4; i++) {
            addVariantIfNew(current, variants, seen);
            addVariantIfNew(current.flip(), variants, seen);

            current = current.rotate();
        }
        return variants;
    }

    private void addVariantIfNew(Shape s, List<Shape> variants, Set<Set<Point>> seen) {
        if (seen.add(s.points)) {
            variants.add(s);
        }
    }

    private Shape rotate() {
        Set<Point> newPts = points.stream()
                .map(p -> new Point(p.c(), -p.r()))
                .collect(Collectors.toSet());
        return new Shape(this.id, newPts);
    }

    private Shape flip() {
        Set<Point> newPts = points.stream()
                .map(p -> new Point(p.r(), -p.c()))
                .collect(Collectors.toSet());
        return new Shape(this.id, newPts);
    }

    public Set<Point> getPoints() {
        return points;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
