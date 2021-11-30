package edu.tamu.wumrwds;

import edu.tamu.wumrwds.entity.Edge;
import edu.tamu.wumrwds.entity.Graph;
import edu.tamu.wumrwds.entity.MaximumBandwidthResult;
import edu.tamu.wumrwds.entity.Vertex;
import edu.tamu.wumrwds.structure.DisjointSet;

import java.util.*;

public class KruskalMbp {

    private KruskalMbp() {
        // private constructor
    }

    public static MaximumBandwidthResult kruskalUsingDisjointSet(Graph graph, Vertex s, Vertex t) {
        // generate max spanning tree
        Map<Integer, List<int[]>> maxSpanningTree = genMaxSpanningTree(graph);

        // find a unique path from s to v
        boolean[] isVisited = new boolean[graph.vertexSize()];

        // dfs
        MaximumBandwidthResult result = new MaximumBandwidthResult(s.getId(), t.getId(), Collections.emptyList(), 0);
        ArrayList<Integer> curPath = new ArrayList<>();
        curPath.add(s.getId());
        dfs(maxSpanningTree, isVisited, s.getId(), t.getId(), curPath, Integer.MAX_VALUE, result);

        return result;
    }


    private static Map<Integer, List<int[]>> genMaxSpanningTree(Graph graph) {
        int n = graph.vertexSize();
        List<Edge> edges = graph.getEdges();

        // sort all edges in descending order
        Collections.sort(edges, (e1, e2) -> (e2.getWeight() - e1.getWeight()));

        // make disjoint set
        DisjointSet disjointSet = new DisjointSet(n);

        Map<Integer, List<int[]>> maxSpanningTree = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            maxSpanningTree.put(i, new LinkedList<>());
        }

        for (Edge edge : edges) {

            Vertex v1 = edge.getV1();
            Vertex v2 = edge.getV2();

            int r1 = disjointSet.findSet(v1.getId());
            int r2 = disjointSet.findSet(v2.getId());

            if (r1 != r2) {
                disjointSet.union(v1.getId(), v2.getId());

                maxSpanningTree.get(v1.getId()).add(new int[]{v2.getId(), edge.getWeight()});
                maxSpanningTree.get(v2.getId()).add(new int[]{v1.getId(), edge.getWeight()});
            }
        }

        return maxSpanningTree;
    }


    private static void dfs(Map<Integer, List<int[]>> maxSpanningTree, boolean[] isVisited, int cur, int end,
                            List<Integer> curPath, int maxBandwidth, MaximumBandwidthResult result) {

        if (result.isCompleted()) {
            return;
        }

        if (cur == end) {
            result.setPath(new ArrayList<>(curPath));
            result.setMaximumBandwidth(maxBandwidth);
            return;
        }

        isVisited[cur] = true;

        List<int[]> neighbors = maxSpanningTree.get(cur);
        for (int[] neighbor : neighbors) {
            if (!isVisited[neighbor[0]]) {
                curPath.add(neighbor[0]);
                dfs(maxSpanningTree, isVisited, neighbor[0], end, curPath, Math.min(maxBandwidth, neighbor[1]), result);
                curPath.remove(curPath.size() - 1);
            }
        }
    }
}
