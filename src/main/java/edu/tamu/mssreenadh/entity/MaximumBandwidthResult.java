package edu.tamu.wumrwds.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaximumBandwidthResult {

    private int from;
    private int to;
    private int[] dad;
    private List<Integer> path;
    private int maximumBandwidth;

    public MaximumBandwidthResult(int from, int to, List<Integer> path, int maximumBandwidth) {
        this.from = from;
        this.to = to;
        this.path = path;
        this.maximumBandwidth = maximumBandwidth;
    }

    public MaximumBandwidthResult(int from, int to, int[] dad, int maximumBandwidth) {
        this.from = from;
        this.to = to;
        this.dad = dad;
        this.maximumBandwidth = maximumBandwidth;

        int v = to;
        path = new ArrayList<>();
        while (v != from) {
            path.add(v);
            v = dad[v];
        }

        path.add(from);

        Collections.reverse(path);
    }

    public boolean isCompleted() {
        return path != null && path.size() != 0;
    }

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    public String printPath() {
        StringBuilder sb = new StringBuilder("MaximumBandwidthPath = [");
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            sb.append("->");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");

        return sb.toString();
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getMaximumBandwidth() {
        return maximumBandwidth;
    }

    public void setMaximumBandwidth(int maximumBandwidth) {
        this.maximumBandwidth = maximumBandwidth;
    }

    public int[] getDad() {
        return dad;
    }

    public void setDad(int[] dad) {
        this.dad = dad;
    }
}
