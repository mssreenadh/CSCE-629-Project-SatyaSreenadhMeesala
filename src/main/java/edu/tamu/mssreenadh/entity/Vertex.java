package edu.tamu.mssreenadh.entity;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
    /** Vertex ID */
    private int id;

    /** Neighbors */
    private List<Vertex> neighbors;

    public Vertex(int id) {
        this.id = id;
        this.neighbors = new LinkedList<>();
    }

    public Vertex(int id, List<Vertex> neighbors) {
        this.id = id;
        this.neighbors = neighbors;
    }

    public int degree() {
        return neighbors.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Vertex> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public String toString() {
        StringBuilder neighborStr = new StringBuilder();
        for (Vertex neighbor : neighbors) {
            neighborStr.append(neighbor.getId());
            neighborStr.append(",");
        }
        neighborStr.deleteCharAt(neighborStr.length() - 1);

        return "Vertex{" +
                "id=" + id +
                ", neighbors=" + neighborStr.toString() +
                '}';
    }
}
