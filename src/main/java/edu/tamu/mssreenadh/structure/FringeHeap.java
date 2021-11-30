package edu.tamu.mssreenadh.structure;

import edu.tamu.mssreenadh.entity.Fringe;

import java.util.*;

public class FringeHeap {

    /** D */
    private final List<Fringe> heap;

    /** H */
    private final Map<Integer, Integer> idxMap;

    public FringeHeap(List<Fringe> elements) {
        heap = new ArrayList<>(elements.size());
        idxMap = new HashMap<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            heap.add(elements.get(i));
            idxMap.put(elements.get(i).getVertex(), i);
        }

        constructHeap();
    }


    public static void main(String[] args) {
        List<Integer> weights = Arrays.asList(3, 7, 9, 8, 10, 2, 14, 1, 16, 4);
        List<Fringe> fringes = new ArrayList<>();
        int i;
        for (i = 0; i < weights.size(); i++) {
            fringes.add(new Fringe(i, weights.get(i)));
        }

        FringeHeap fringeHeap = new FringeHeap(fringes);
        System.out.println(fringeHeap);

        fringeHeap.insert(new Fringe(i++, 1));
        fringeHeap.insert(new Fringe(i++, 23));
        fringeHeap.insert(new Fringe(i++, 4));
        fringeHeap.insert(new Fringe(i++, 17));
        fringeHeap.insert(new Fringe(i++, 7));

        fringeHeap.insert(new Fringe(0, 1000));
        fringeHeap.delete(0);

        fringeHeap.insert(new Fringe(i++, 5));
        fringeHeap.insert(new Fringe(i++, 11));
        fringeHeap.insert(new Fringe(i++, 0));

        fringeHeap.delete(3);

        fringeHeap.insert(new Fringe(i++, 32));
        fringeHeap.insert(new Fringe(i++, 131));

        fringeHeap.delete(5);

        fringeHeap.insert(new Fringe(i, 0));


        while (!fringeHeap.isEmpty()) {
            Fringe fringe = fringeHeap.extractMax();
            System.out.println(fringe);
        }
    }





    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public Fringe extractMax() {
        Fringe maximum = heap.get(0);

        delete(maximum.getVertex());

        return maximum;
    }

    public void insert(Fringe element) {
        heap.add(element);
        int i = size() - 1;
        idxMap.put(element.getVertex(), i);

        bottomUpHeapify(i);
    }

    public void delete(int vertexId) {
        Integer i = idxMap.get(vertexId);

        swap(i, size() - 1);
        heap.remove(size() - 1);
        idxMap.remove(vertexId);

        bottomUpHeapify(i);
        topDownHeapify(i);
    }








    private void constructHeap() {
        for (int i = size() / 2; i >= 0; i--) {
            topDownHeapify(i);
        }
    }

    private void topDownHeapify(int i) {
        int l = left(i);
        int r = right(i);

        int largest;
        if (l < size() && heap.get(l).compareTo(heap.get(i)) > 0)  {
            largest = l;
        }
        else {
            largest = i;
        }

        if (r < size() && heap.get(r).compareTo(heap.get(largest)) > 0) {
            largest = r;
        }

        if (largest != i) {
            swap(i, largest);
            topDownHeapify(largest);
        }
    }

    private void bottomUpHeapify(int i) {
        while (i > 0 && i < size() && heap.get(parent(i)).compareTo(heap.get(i)) < 0) {
            swap(parent(i), i);
            i = parent(i);
        }
    }

    private void swap(int i, int j) {
        Fringe v1 = heap.get(i);
        Fringe v2 = heap.get(j);
        heap.set(i, v2);
        heap.set(j, v1);

        idxMap.put(v1.getVertex(), j);
        idxMap.put(v2.getVertex(), i);
    }

    private static int parent(int i) {
        return i / 2;
    }

    private static int left(int i) {
        return 2 * i;
    }

    private static int right(int i) {
        return 2 * i + 1;
    }
}
