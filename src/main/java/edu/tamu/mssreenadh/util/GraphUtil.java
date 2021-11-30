package edu.tamu.wumrwds.util;

import edu.tamu.wumrwds.entity.Edge;
import edu.tamu.wumrwds.entity.Graph;
import edu.tamu.wumrwds.entity.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GraphUtil {

    private static final int WEIGHT_LOWER_BOUND = 1;
    private static final int WEIGHT_UPPER_BOUND = Integer.MAX_VALUE;

    private GraphUtil() {
        // private constructor
    }

    /**
     * Help function for generating G1.
     *
     * @param size
     * @param averageDegree
     * @return
     */
    public static Graph genGraphWithAverageDegree(int size, int averageDegree) {
        // create vertices
        List<Vertex> vertices = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            vertices.add(new Vertex(i));
        }

        // connect all vertices to avoid the unconnected case
        boolean[][] isExisted = new boolean[size][size];
        List<Edge> edges = new LinkedList<>();
        for (int i = 0; i < size - 1; i++) {
            isExisted[vertices.get(i).getId()][vertices.get(i + 1).getId()] = true;
            addEdge(i, i + 1, vertices, edges);
        }

        // pick distinct pairs of vertices
        int extraEdgesAmount = (averageDegree - 2) * size / 2;
        for (int i = 0; i < extraEdgesAmount + 1; i++) {
            int[] pair = randomPickUniquePair(size, isExisted);
            addEdge(pair[0], pair[1], vertices, edges);
        }

        // construct graph by the previously generated vertices and edges
        return new Graph(vertices, edges);
    }

    /**
     * Help function for generating G2.
     *
     * @param size
     * @param adjacencyRatio
     * @param slack
     * @return
     */
    public static Graph genGraphWithSpecificAmountOfNeighbors(int size, double adjacencyRatio, double slack) {
        // create vertices
        List<Vertex> vertices = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            vertices.add(new Vertex(i));
        }

        // get a random degree distribution
        int[] degreeDistribution = new int[size];
        int averageDegree = (int) Math.round(size * adjacencyRatio);
        int halfRange = (int) Math.round(slack * size);
        for (int i = 0; i < size; i++) {
            degreeDistribution[i] = randomIntBetween(-halfRange, halfRange) + averageDegree;
        }

        // connect all vertices to avoid the unconnected case
        boolean[][] isExisted = new boolean[size][size];
        int[] degrees = new int[size];
        List<Edge> edges = new LinkedList<>();
        for (int i = 0; i < size - 1; i++) {
            addEdge(i, i+1, vertices, edges);
            isExisted[i][i+1] = true;
            degrees[i]++;
            degrees[i+1]++;
        }


        for (int vertex = 0; vertex < size - 1; vertex++) {
            int pickTimes = (int) Math.floor((1 - 2 * slack) * degreeDistribution[vertex]) - degrees[vertex];
            if (pickTimes > 0) {
                for (int i = 0; i < pickTimes; i++) {
                    int[] pair = randomPickUniqueVertex(size, vertex, isExisted, degrees, degreeDistribution, slack);
                    addEdge(pair[0], pair[1], vertices, edges);
                }
            }

        }

        return new Graph(vertices, edges);
    }







    public static void main(String[] args) {
        Graph g1 = genGraphWithAverageDegree(5000, 6);
        System.out.println(g1);


        System.out.println(g1.totalDegree());
        System.out.println(g1.averageDegree());
        System.out.println(g1.edgeSize());
        System.out.println(g1.getVertices());

        System.out.println("====================");


        Graph g2 = genGraphWithSpecificAmountOfNeighbors(5000, 0.2, 0.01);

        System.out.println(g2.totalDegree());
        System.out.println(g2.averageDegree());
        System.out.println(g2.edgeSize());
        int[] degrees = new int[5000];
        for (int i = 0; i < 5000; i++) {
            degrees[i] = g2.getVertices().get(i).degree();
        }
        System.out.println(Arrays.toString(degrees));
    }









    private static void addEdge(int v1, int v2, List<Vertex> vertices, List<Edge> edges) {
        if (v1 > v2) {
            int tmp = v1;
            v1 = v2;
            v2 = tmp;
        }

        vertices.get(v1).getNeighbors().add(vertices.get(v2));
        vertices.get(v2).getNeighbors().add(vertices.get(v1));

        edges.add(new Edge(vertices.get(v1), vertices.get(v2), randomWeight()));
    }

    private static int[] randomPickUniqueVertex(int size, int vertex, boolean[][] isExisted, int[] degrees,
                                                int[] degreeDistribution, double slack) {

        int v = randomIntBetween(0, size - 1);

        if (degrees[v] >= Math.ceil(degreeDistribution[v] + slack * degreeDistribution[v]) || v == vertex) {
            return randomPickUniqueVertex(size, vertex, isExisted, degrees, degreeDistribution, slack);
        }

        int v1 = Math.min(v, vertex), v2 = Math.max(v, vertex);

        if (isExisted[v1][v2]) {
            return randomPickUniqueVertex(size, vertex, isExisted, degrees, degreeDistribution, slack);
        }

        isExisted[v1][v2] = true;
        degrees[v1]++;
        degrees[v2]++;
        return new int[]{v1, v2};
    }

    private static int[] randomPickUniquePair(int size, boolean[][] isExisted) {
        int v1 = randomIntBetween(0, size - 1);
        int v2 = randomIntBetween(0, size - 1);

        if (v1 > v2) {
            int tmp = v1;
            v1 = v2;
            v2 = tmp;
        }

        if (isExisted[v1][v2] || v1 == v2) {
            return randomPickUniquePair(size, isExisted);
        }

        isExisted[v1][v2] = true;
        return new int[]{v1, v2};
    }

    private static int randomWeight() {
        return randomIntBetween(WEIGHT_LOWER_BOUND, WEIGHT_UPPER_BOUND);
    }

    private static int randomIntBetween(int l, int u) {
        return ThreadLocalRandom.current().nextInt(u - l + 1) + l;
    }
}
