package edu.tamu.mssreenadh.structure;

public class DisjointSet {

    private final int[] rank;
    private final int[] dad;

    /**
     * Makes sets.
     *
     * @param n
     */
    public DisjointSet(int n) {
        rank = new int[n];
        dad = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = 1;
            dad[i] = i;
        }
    }

    /**
     * Finds set representative with path compression.
     *
     * @param x
     * @return
     */
    public int findSet(int x) {
        if (dad[x] != x) {
            dad[x] = findSet(dad[x]);
        }
        return dad[x];
    }

    /**
     * Links two sets.
     *
     * @param p1
     * @param p2
     */
    private void link(int p1, int p2) {
        if (rank[p1] < rank[p2]) {
            // set1 is less than set2
            // make p2 the parent(root) of p1
            dad[p1] = p2;
        } else {
            // set1 is larger than set2
            // make p1 the parent(root) of p2
            dad[p2] = p1;

            // set1 is as the same rank as set2
            // increase the rank of the root p1
            if (rank[p1] == rank[p2]) {
                rank[p1]++;
            }
        }
    }

    public void union(int x, int y) {
        link(findSet(x), findSet(y));
    }


    public static void main(String[] args) {

        DisjointSet set = new DisjointSet(10);

        set.union(1, 2);
        set.union(2, 3);
        set.union(4, 7);
        set.union(5, 6);
        set.union(8, 7);

        System.out.println(set.findSet(0));
        System.out.println(set.findSet(1));
        System.out.println(set.findSet(2));
        System.out.println(set.findSet(3));
        System.out.println(set.findSet(4));
        System.out.println(set.findSet(5));
        System.out.println(set.findSet(6));
        System.out.println(set.findSet(7));
        System.out.println(set.findSet(8));
        System.out.println(set.findSet(9));
    }
}
