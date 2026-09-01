
public class BookIsbnHashTable {

    private static class HashNode {

        String isbn;
        String bookTitle;
        HashNode next;

        HashNode(String isbn, String bookTitle, HashNode next) {
            this.isbn = isbn;
            this.bookTitle = bookTitle;
            this.next = next;
        }
    }

    private HashNode[] table;
    private int capacity;
    private int size;

    public BookIsbnHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
        this.size = 0;
    }

    private int hash(String isbn) {
        return (isbn.hashCode() & 0x7fffffff) % capacity;
    }

    public void put(String isbn, String bookTitle) {
        int idx = hash(isbn);
        for (HashNode curr = table[idx]; curr != null; curr = curr.next) {
            if (curr.isbn.equals(isbn)) {
                curr.bookTitle = bookTitle; // 更新
                return;
            }
        }
        table[idx] = new HashNode(isbn, bookTitle, table[idx]);
        size++;
    }

    public String get(String isbn) {
        int idx = hash(isbn);
        for (HashNode curr = table[idx]; curr != null; curr = curr.next) {
            if (curr.isbn.equals(isbn)) {
                return curr.bookTitle;
            }
        }
        return null;
    }

    public boolean remove(String isbn) {
        int idx = hash(isbn);
        HashNode curr = table[idx];
        HashNode prev = null;
        while (curr != null) {
            if (curr.isbn.equals(isbn)) {
                if (prev == null) {
                    table[idx] = curr.next; 
                }else {
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

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    public void printBucketReport() {
        System.out.printf("===== 圖書 ISBN 哈希表報告 (容量: %d, 藏書量: %d, 負載因子: %.2f) =====%n",
                capacity, size, getLoadFactor());
        for (int i = 0; i < capacity; i++) {
            System.out.printf("Bucket %2d: ", i);
            HashNode curr = table[i];
            if (curr == null) {
                System.out.println("[空]");
            } else {
                StringBuilder sb = new StringBuilder();
                while (curr != null) {
                    sb.append(String.format("[%s: %s] -> ", curr.isbn, curr.bookTitle));
                    curr = curr.next;
                }
                sb.append("null");
                System.out.println(sb);
            }
        }
    }

    public static void main(String[] args) {
        BookIsbnHashTable ht = new BookIsbnHashTable(5);
        ht.put("978-986-123", "資料結構實務");
        ht.put("978-986-456", "演算法導論");
        ht.put("978-986-789", "Java 程式設計入門");
        ht.put("978-986-111", "資料庫系統概論");

        ht.printBucketReport();
        System.out.println("\n查詢 978-986-456: " + ht.get("978-986-456"));
    }
}
