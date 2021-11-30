package edu.tamu.wumrwds.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    /** Vertex Set */
    private List<Vertex> vertices;

    /** Edge Set */
    private List<Edge> edges;

    /** Edge Dictionary */
    private final Map<String, Edge> edgeDict;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;

        edgeDict = new HashMap<>();
        for (Edge edge : edges) {
            edgeDict.put(String.format("%s-%s", edge.getV1().getId(), edge.getV2().getId()), edge);
        }
    }



    public int vertexSize() {
        return vertices.size();
    }

    public int edgeSize() {
        return edges.size();
    }

    public int totalDegree() {
        int degreeSum = 0;
        for (Vertex vertex : vertices) {
            degreeSum += vertex.degree();
        }
        return degreeSum;
    }

    public Vertex getVertxById(int vertexId) {
        return vertices.get(vertexId);
    }

    public int getWeight(Vertex v1, Vertex v2) {
        if (v1.getId() < v2.getId()) {
            return edgeDict.get(String.format("%s-%s", v1.getId(), v2.getId())).getWeight();
        }

        return edgeDict.get(String.format("%s-%s", v2.getId(), v1.getId())).getWeight();
    }

    public double averageDegree() {
        return (double) totalDegree() / vertexSize();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                '}';
    }
}
