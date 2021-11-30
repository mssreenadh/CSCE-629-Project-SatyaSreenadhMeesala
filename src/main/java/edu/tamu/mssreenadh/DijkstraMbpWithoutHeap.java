package edu.tamu.wumrwds;

import edu.tamu.wumrwds.entity.Graph;
import edu.tamu.wumrwds.entity.MaximumBandwidthResult;
import edu.tamu.wumrwds.entity.Vertex;

import java.util.ArrayList;
import java.util.List;

public class DijkstraMbpWithoutHeap {
    private static final int UNSEEN = 0;
    private static final int FRINGE = 1;
    private static final int INTREE = 2;

    private DijkstraMbpWithoutHeap() {
        // private constructor
    }

    public static MaximumBandwidthResult dijkstraNotUsingHeap(Graph graph, Vertex s, Vertex t) {
        int n = graph.vertexSize();

        // define status, wt & dad
        int[] status = new int[n];
        int[] wt = new int[n];
        int[] dad = new int[n];

        // set source vertex's status to INTREE
        status[s.getId()] = INTREE;

        //
        List<int[]> fringes = new ArrayList<>();
        for (Vertex w : s.getNeighbors()) {
            status[w.getId()] = FRINGE;
            fringes.add(new int[]{w.getId(), graph.getWeight(s, w)});
            wt[w.getId()] = graph.getWeight(s, w);
            dad[w.getId()] = s.getId();
        }

        while (!fringes.isEmpty()) {
            int[] bestFringe = extractMax(fringes);
            Vertex v = graph.getVertxById(bestFringe[0]);
            status[v.getId()] = INTREE;

            for (Vertex w : v.getNeighbors()) {
                int weight = graph.getWeight(v, w);

                if (status[w.getId()] == UNSEEN) {
                    status[w.getId()] = FRINGE;
                    dad[w.getId()] = v.getId();
                    wt[w.getId()] = Math.min(weight, wt[v.getId()]);
                    fringes.add(new int[]{w.getId(), weight});
                } else if (status[w.getId()] == FRINGE && wt[w.getId()] < Math.min(weight, wt[v.getId()])) {
                    dad[w.getId()] = v.getId();
                    wt[w.getId()] = Math.min(weight, wt[v.getId()]);
                    update(fringes, w.getId(), wt[w.getId()]);
                }
            }
        }

        return new MaximumBandwidthResult(s.getId(), t.getId(), dad, wt[t.getId()]);
    }

    private static int[] extractMax(List<int[]> fringes) {
        int[] max = new int[]{-1, -1};
        int maxIdx = -1;
        for (int i = 0; i < fringes.size(); i++) {
            if (max[1] < fringes.get(i)[1]) {
                max[0] = fringes.get(i)[0];
                max[1] = fringes.get(i)[1];
                maxIdx = i;
            }
        }

        // remove the best fringe
        fringes.remove(maxIdx);

        return max;
    }

    private static void update(List<int[]> fringes, int vertexId, int updatedWt) {
        for (int[] fringe : fringes) {
            if (vertexId == fringe[0]) {
                fringe[1] = updatedWt;
                return;
            }
        }
    }
}
