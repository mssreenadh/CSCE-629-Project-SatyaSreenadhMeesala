package edu.tamu.mssreenadh;

import edu.tamu.mssreenadh.entity.Fringe;
import edu.tamu.mssreenadh.entity.Graph;
import edu.tamu.mssreenadh.entity.MaximumBandwidthResult;
import edu.tamu.mssreenadh.entity.Vertex;
import edu.tamu.mssreenadh.structure.FringeHeap;

import java.util.LinkedList;
import java.util.List;

public class DijkstraMbpWithHeap {
    private static final int UNSEEN = 0;
    private static final int FRINGE = 1;
    private static final int INTREE = 2;

    private DijkstraMbpWithHeap() {
        // private constructor
    }

    public static MaximumBandwidthResult dijkstraUsingHeap(Graph graph, Vertex s, Vertex t) {
        int n = graph.vertexSize();

        // define status, wt & dad
        int[] status = new int[n];
        int[] wt = new int[n];
        int[] dad = new int[n];

        // set source vertex's status to INTREE
        status[s.getId()] = INTREE;

        // get the initial fringes and use them to construct a heap
        List<Fringe> fringes = new LinkedList<>();
        for (Vertex w : s.getNeighbors()) {
            status[w.getId()] = FRINGE;
            fringes.add(new Fringe(w.getId(), graph.getWeight(s, w)));
            wt[w.getId()] = graph.getWeight(s, w);
            dad[w.getId()] = s.getId();
        }
        FringeHeap maxFringeHeap = new FringeHeap(fringes);


        while (!maxFringeHeap.isEmpty()) {
            // extract the maximum fringe vertex
            Fringe bestFringe = maxFringeHeap.extractMax();
            Vertex v = graph.getVertxById(bestFringe.getVertex());
            status[v.getId()] = INTREE;

            for (Vertex w : v.getNeighbors()) {
                int weight = graph.getWeight(v, w);

                if (status[w.getId()] == UNSEEN) {
                    status[w.getId()] = FRINGE;
                    dad[w.getId()] = v.getId();
                    wt[w.getId()] = Math.min(weight, wt[v.getId()]);
                    maxFringeHeap.insert(new Fringe(w.getId(), weight));
                } else if (status[w.getId()] == FRINGE && wt[w.getId()] < Math.min(weight, wt[v.getId()])) {
                    maxFringeHeap.delete(w.getId());
                    dad[w.getId()] = v.getId();
                    wt[w.getId()] = Math.min(weight, wt[v.getId()]);
                    maxFringeHeap.insert(new Fringe(w.getId(), wt[w.getId()]));
                }
            }
        }

        return new MaximumBandwidthResult(s.getId(), t.getId(), dad, wt[t.getId()]);
    }
}
