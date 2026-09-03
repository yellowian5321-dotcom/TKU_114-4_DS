
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q04_ChainedHashTable {

    private static class Entry {

        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> buckets;
    private final int capacity;
    private int size = 0;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("Bucket count must be positive.");
        }
        this.capacity = bucketCount;
        this.buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    private int getIndex(int key) {
        return Math.floorMod(key, capacity);
    }

    public void put(int key, String value) {
        int idx = getIndex(key);
        List<Entry> bucket = buckets.get(idx);
        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int idx = getIndex(key);
        for (Entry entry : buckets.get(idx)) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        int idx = getIndex(key);
        List<Entry> bucket = buckets.get(idx);
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).key == key) {
                bucket.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int max = 0;
        for (List<Entry> bucket : buckets) {
            max = Math.max(max, bucket.size());
        }
        return max;
    }
}
