package edu.tamu.wumrwds.entity;

public class Edge {
    /** Vertex 1 */
    private Vertex v1;

    /** Vertex 2 */
    private Vertex v2;

    /** Weight */
    private int weight;

    public Edge(Vertex v1, Vertex v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Vertex getV1() {
        return v1;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "v1=" + v1.getId() +
                ", v2=" + v2.getId() +
                ", weight=" + weight +
                '}';
    }
}
