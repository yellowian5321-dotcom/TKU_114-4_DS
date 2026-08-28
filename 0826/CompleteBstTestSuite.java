
import java.util.ArrayList;
import java.util.List;

public class CompleteBstTestSuite {

    static class SimpleBst {

        static class Node {

            int key;
            Node left, right;

            Node(int key) {
                this.key = key;
            }
        }
        Node root;

        boolean insert(int key) {
            if (contains(key)) {
                return false;
            }
            root = insertRec(root, key);
            return true;
        }

        private Node insertRec(Node n, int key) {
            if (n == null) {
                return new Node(key);
            }
            if (key < n.key) {
                n.left = insertRec(n.left, key); 
            }else if (key > n.key) {
                n.right = insertRec(n.right, key);
            }
            return n;
        }

        boolean contains(int key) {
            Node c = root;
            while (c != null) {
                if (key == c.key) {
                    return true;
                }
                c = (key < c.key) ? c.left : c.right;
            }
            return false;
        }

        boolean delete(int key) {
            if (!contains(key)) {
                return false;
            }
            root = deleteRec(root, key);
            return true;
        }

        private Node deleteRec(Node n, int key) {
            if (n == null) {
                return null;
            }
            if (key < n.key) {
                n.left = deleteRec(n.left, key); 
            }else if (key > n.key) {
                n.right = deleteRec(n.right, key); 
            }else {
                if (n.left == null) {
                    return n.right;
                }
                if (n.right == null) {
                    return n.left;
                }
                Node m = n.right;
                while (m.left != null) {
                    m = m.left;
                }
                n.key = m.key;
                n.right = deleteRec(n.right, m.key);
            }
            return n;
        }

        int size() {
            return sizeRec(root);
        }

        private int sizeRec(Node n) {
            return n == null ? 0 : 1 + sizeRec(n.left) + sizeRec(n.right);
        }

        boolean isValid() {
            return isValidRec(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean isValidRec(Node n, long min, long max) {
            if (n == null) {
                return true;
            }
            if (n.key <= min || n.key >= max) {
                return false;
            }
            return isValidRec(n.left, min, n.key) && isValidRec(n.right, n.key, max);
        }

        List<Integer> range(int low, int high) {
            List<Integer> list = new ArrayList<>();
            rangeRec(root, low, high, list);
            return list;
        }

        private void rangeRec(Node n, int low, int high, List<Integer> list) {
            if (n == null) {
                return;
            }
            if (n.key > low) {
                rangeRec(n.left, low, high, list);
            }
            if (n.key >= low && n.key <= high) {
                list.add(n.key);
            }
            if (n.key < high) {
                rangeRec(n.right, low, high, list);
            }
        }
    }

    private static int passCount = 0;
    private static int failCount = 0;

    public static void check(String description, boolean condition) {
        if (condition) {
            System.out.printf("[PASS] %s%n", description);
            passCount++;
        } else {
            System.out.printf("[FAIL] %s%n", description);
            failCount++;
        }
    }

    public static void main(String[] args) {
        SimpleBst bst = new SimpleBst();

        // 1-3 Empty Tree Tests
        check("1. 初始空樹 size 應為 0", bst.size() == 0);
        check("2. 空樹 contains 應為 false", !bst.contains(50));
        check("3. 空樹刪除應回傳 false", !bst.delete(50));

        // 4-6 Insert & Root Tests
        check("4. 插入根節點 50 成功", bst.insert(50));
        check("5. 根節點 size 應為 1", bst.size() == 1);
        check("6. 樹中包含根節點 50", bst.contains(50));

        // 7-8 Duplicate Tests
        check("7. 重複插入 50 應回傳 false", !bst.insert(50));
        check("8. 重複插入後 size 維持 1", bst.size() == 1);

        // 9-11 Left/Right Insertion & Invariant
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
        check("9. 插入多節點後 size 應為 7", bst.size() == 7);
        check("10. 樹結構符合 BST Invariant", bst.isValid());
        check("11. 搜尋左子樹葉節點 20 存在", bst.contains(20));

        // 12 Missing Element Test
        check("12. 搜尋不存在元素 99 回傳 false", !bst.contains(99));

        // 13-14 Range Queries
        List<Integer> r = bst.range(30, 70);
        check("13. 範圍查詢 [30, 70] 筆數應為 5", r.size() == 5);
        check("14. 範圍查詢不合法區間 [80, 20] 回傳空", bst.range(80, 20).isEmpty());

        // 15 Delete Missing
        check("15. 刪除不存在元素 99 應回傳 false", !bst.delete(99));

        // 16 Delete Leaf
        check("16. 刪除葉節點 20 成功", bst.delete(20) && !bst.contains(20));
        check("17. 刪除葉節點後仍符合 Invariant", bst.isValid());

        // 18 Delete One Child Node
        bst.insert(25); // 讓 30 只有右節點 (40) 與左節點 (25) 先刪一個
        bst.delete(40);
        check("18. 刪除單一子節點結構成功", bst.delete(30) && bst.contains(25));

        // 19 Delete Two Children Node
        check("19. 刪除具雙子節點之 70 成功", bst.delete(70) && bst.isValid());

        // 20 Delete Root Node (Two children)
        check("20. 刪除根節點 50 成功且符合 Invariant", bst.delete(50) && bst.isValid());

        // 21 Final Size & Verification
        check("21. 最終樹狀態依然為合法 BST", bst.isValid());

        System.out.printf("%n=== 測試總結: %d PASS / %d FAIL ===%n", passCount, failCount);
    }
}
