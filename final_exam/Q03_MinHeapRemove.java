
import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {

    private final List<Integer> heap;

    public Q03_MinHeapRemove(List<Integer> values) {
        this.heap = new ArrayList<>();
        if (values != null) {
            for (Integer val : values) {
                if (val != null) {
                    heap.add(val);
                }
            }
        }
        bottomUpHeapify();
    }

    private void bottomUpHeapify() {
        if (heap.size() <= 1) {
            return;
        }
        for (int i = (heap.size() - 2) / 2; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public Integer removeMin() {
        if (heap.isEmpty()) {
            return null;
        }
        int min = heap.get(0);
        int last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            bubbleDown(0);
        }
        return min;
    }

    private void bubbleDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < size && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }
}
