package software.aoc.day08.a.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import software.aoc.day08.a.model.Connection;
import software.aoc.day08.a.model.Point3D;
import software.aoc.day08.a.model.UnionFind;
import software.aoc.day08.a.parser.PointParser;

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

        List<Connection> allConnections = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long distSq = points.get(i).distanceSquared(points.get(j));
                allConnections.add(new Connection(i, j, distSq));
            }
        }

        Collections.sort(allConnections);

        UnionFind uf = new UnionFind(n);

        allConnections.stream()
                .limit(connectionsToMake)
                .forEach(conn -> uf.union(conn.u(), conn.v()));

        List<Integer> sizes = new ArrayList<>(uf.getComponentSizes().values());

        sizes.sort(Comparator.reverseOrder());

        if (sizes.isEmpty())
            return 0;

        long result = 1;
        int limit = Math.min(3, sizes.size());

        for (int i = 0; i < limit; i++) {
            result *= sizes.get(i);
        }

        return result;
    }
}
