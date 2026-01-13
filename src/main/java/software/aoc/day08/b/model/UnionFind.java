package software.aoc.day08.b.model;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private final int[] parent;
    private final int[] size;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {
        if (parent[p] == p)
            return p;
        parent[p] = find(parent[p]);
        return parent[p];
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ)
            return;

        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public int getCount() {
        return count;
    }

    public Map<Integer, Integer> getComponentSizes() {
        Map<Integer, Integer> componentSizes = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            componentSizes.put(root, size[root]);
        }
        return componentSizes;
    }
}
