
import java.util.Arrays;

public class TreeShapeComparison {

    static class Node {

        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    static class TreeMetrics {

        Node root;

        void insert(int key) {
            root = insertRec(root, key);
        }

        private Node insertRec(Node current, int key) {
            if (current == null) {
                return new Node(key);
            }
            if (key < current.key) {
                current.left = insertRec(current.left, key); 
            }else if (key > current.key) {
                current.right = insertRec(current.right, key);
            }
            return current;
        }

        int getHeight() {
            return heightRec(root);
        }

        private int heightRec(Node n) {
            return n == null ? 0 : 1 + Math.max(heightRec(n.left), heightRec(n.right));
        }

        // 回傳搜尋此 key 經歷的比較次數 (若找到或至 null 的路徑節點數)
        int searchComparisons(int target) {
            int cmpCount = 0;
            Node cur = root;
            while (cur != null) {
                cmpCount++;
                if (target == cur.key) {
                    break;
                }
                cur = (target < cur.key) ? cur.left : cur.right;
            }
            return cmpCount;
        }
    }

    public static void main(String[] args) {
        int[] baseKeys = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150}; // 15 個鍵
        int[] ascending = Arrays.copyOf(baseKeys, baseKeys.length);

        int[] descending = new int[15];
        for (int i = 0; i < 15; i++) {
            descending[i] = baseKeys[14 - i];
        }

        // 接近平衡的插入順序 (二分中位數順序)
        int[] balanced = {80, 40, 120, 20, 60, 100, 140, 10, 30, 50, 70, 90, 110, 130, 150};

        TreeMetrics ascTree = buildTree(ascending);
        TreeMetrics descTree = buildTree(descending);
        TreeMetrics balTree = buildTree(balanced);

        int[] missingKeys = {5, 25, 75, 125, 200};

        printComparison("升冪順序樹 (Ascending)", ascTree, baseKeys, missingKeys);
        printComparison("降冪順序樹 (Descending)", descTree, baseKeys, missingKeys);
        printComparison("平衡順序樹 (Balanced)", balTree, baseKeys, missingKeys);
    }

    private static TreeMetrics buildTree(int[] keys) {
        TreeMetrics tm = new TreeMetrics();
        for (int k : keys) {
            tm.insert(k);
        }
        return tm;
    }

    private static void printComparison(String name, TreeMetrics tree, int[] presentKeys, int[] missingKeys) {
        int totalPresentCmp = 0;
        for (int k : presentKeys) {
            totalPresentCmp += tree.searchComparisons(k);
        }

        int totalMissingCmp = 0;
        for (int k : missingKeys) {
            totalMissingCmp += tree.searchComparisons(k);
        }

        System.out.println("=== " + name + " ===");
        System.out.println("樹的高度: " + tree.getHeight());
        System.out.println("15 個現存鍵的搜尋比較總次數: " + totalPresentCmp + " (平均: " + String.format("%.2f", totalPresentCmp / 15.0) + ")");
        System.out.println("5 個缺失鍵的搜尋比較總次數:   " + totalMissingCmp + " (平均: " + String.format("%.2f", totalMissingCmp / 5.0) + ")");
        System.out.println();
    }
}
