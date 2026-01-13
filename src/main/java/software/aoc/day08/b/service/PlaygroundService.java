package software.aoc.day08.b.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import software.aoc.day08.b.model.Connection;
import software.aoc.day08.b.model.Point3D;
import software.aoc.day08.b.model.UnionFind;
import software.aoc.day08.b.parser.PointParser;

public class PlaygroundService {

    private final PointParser parser;

    public PlaygroundService() {
        this.parser = new PointParser();
    }

    public long calculateCircuitPower(List<String> rawInput, int connectionsToMake) {
        List<Point3D> points = parser.parse(rawInput);
        int n = points.size();
        if (n < 2)
            return 0;

        List<Connection> allConnections = generateAllConnections(points);
        Collections.sort(allConnections);

        UnionFind uf = new UnionFind(n);
        allConnections.stream().limit(connectionsToMake).forEach(c -> uf.union(c.u(), c.v()));

        List<Integer> sizes = new ArrayList<>(uf.getComponentSizes().values());
        sizes.sort(Collections.reverseOrder());
        long result = 1;
        for (int i = 0; i < Math.min(3, sizes.size()); i++)
            result *= sizes.get(i);
        return result;
    }

    public long findLastConnectionCoordinatesProduct(List<String> rawInput) {
        List<Point3D> points = parser.parse(rawInput);
        int n = points.size();
        if (n < 2)
            return 0;

        List<Connection> allConnections = generateAllConnections(points);
        Collections.sort(allConnections);

        UnionFind uf = new UnionFind(n);

        for (Connection conn : allConnections) {
            if (uf.find(conn.u()) != uf.find(conn.v())) {
                uf.union(conn.u(), conn.v());

                if (uf.getCount() == 1) {
                    Point3D p1 = points.get(conn.u());
                    Point3D p2 = points.get(conn.v());
                    return (long) p1.x() * p2.x();
                }
            }
        }

        throw new IllegalStateException("Graph is not fully connected even after using all edges");
    }

    private List<Connection> generateAllConnections(List<Point3D> points) {
        List<Connection> conns = new ArrayList<>();
        int n = points.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long distSq = points.get(i).distanceSquared(points.get(j));
                conns.add(new Connection(i, j, distSq));
            }
        }
        return conns;
    }
}
