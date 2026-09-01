
public class ResizableStringMap {

    private static class Entry {

        String key;
        String value;
        Entry next;

        Entry(String key, String value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public ResizableStringMap(int initialCapacity) {
        this.buckets = new Entry[Math.max(1, initialCapacity)];
        this.size = 0;
    }

    public ResizableStringMap() {
        this(7);
    }

    private int getBucketIndex(String key, int capacity) {
        if (key == null) {
            return 0;
        }
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(String key, String value) {
        int index = getBucketIndex(key, buckets.length);
        for (Entry curr = buckets[index]; curr != null; curr = curr.next) {
            if (curr.key.equals(key)) {
                curr.value = value;
                return;
            }
        }
        buckets[index] = new Entry(key, value, buckets[index]);
        size++;

        if ((double) size / buckets.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public String get(String key) {
        int index = getBucketIndex(key, buckets.length);
        for (Entry curr = buckets[index]; curr != null; curr = curr.next) {
            if (curr.key.equals(key)) {
                return curr.value;
            }
        }
        return null;
    }

    public boolean remove(String key) {
        int index = getBucketIndex(key, buckets.length);
        Entry curr = buckets[index];
        Entry prev = null;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    private void resize() {
        int newCapacity = buckets.length * 2 + 1;
        Entry[] oldBuckets = buckets;
        buckets = new Entry[newCapacity];
        size = 0;

        for (Entry head : oldBuckets) {
            Entry curr = head;
            while (curr != null) {
                put(curr.key, curr.value);
                curr = curr.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public int getBucketCount() {
        return buckets.length;
    }

    public void printBucketDistribution() {
        System.out.printf("--- 桶數分佈 (Size: %d, Buckets: %d, Load Factor: %.2f) ---%n",
                size, buckets.length, (double) size / buckets.length);
        for (int i = 0; i < buckets.length; i++) {
            int count = 0;
            for (Entry c = buckets[i]; c != null; c = c.next) {
                count++;
            }
            System.out.printf("Bucket %2d: %d entries%n", i, count);
        }
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(3);
        map.put("A", "Alpha");
        map.put("B", "Beta");
        map.put("C", "Gamma");
        map.printBucketDistribution();
        map.put("D", "Delta"); // 觸發擴展
        map.printBucketDistribution();
    }
}
