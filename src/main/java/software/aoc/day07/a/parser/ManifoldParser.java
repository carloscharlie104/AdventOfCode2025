package software.aoc.day07.a.parser;

import java.util.List;
import software.aoc.day07.a.model.Beam;
import software.aoc.day07.a.model.Manifold;

public class ManifoldParser {

    public Manifold parse(List<String> lines) {
        int h = lines.size();
        int w = lines.get(0).length();
        char[][] newGrid = new char[h][w];
        Beam start = null;

        for (int r = 0; r < h; r++) {
            String line = lines.get(r);
            for (int c = 0; c < w; c++) {
                char ch = line.charAt(c);
                newGrid[r][c] = ch;
                if (ch == 'S') {
                    start = new Beam(r, c);
                }
            }
        }

        if (start == null)
            throw new IllegalArgumentException("No start point 'S' found");
        return new Manifold(newGrid, w, h, start);
    }
}
