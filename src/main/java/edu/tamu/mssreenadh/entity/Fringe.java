package edu.tamu.wumrwds.entity;

public class Fringe implements Comparable<Fringe> {
    private int vertex;
    private int weight;

    public Fringe(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Fringe{" +
                "vertex=" + vertex +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Fringe o) {
        return weight - o.weight;
    }
}
