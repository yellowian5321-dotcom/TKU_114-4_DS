
import java.util.ArrayList;
import java.util.List;

public class TreeBugLab {

    static class Node {

        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    // Bug 1: 搜尋方向反轉
    static boolean buggySearch(Node root, int target) {
        if (root == null) {
            return false;
        }
        if (root.val == target) {
            return true;
        }
        if (target < root.val) {
            return buggySearch(root.right, target); // BUG: 應往左卻往右

        }
        return buggySearch(root.left, target);
    }

    static boolean fixedSearch(Node root, int target) {
        if (root == null) {
            return false;
        }
        if (root.val == target) {
            return true;
        }
        if (target < root.val) {
            return fixedSearch(root.left, target);
        }
        return fixedSearch(root.right, target);
    }

    // Bug 2: 中序走訪順序寫成前序
    static void buggyInorder(Node root, List<Integer> list) {
        if (root == null) {
            return;
        }
        list.add(root.val); // BUG: 先訪問根節點
        buggyInorder(root.left, list);
        buggyInorder(root.right, list);
    }

    static void fixedInorder(Node root, List<Integer> list) {
        if (root == null) {
            return;
        }
        fixedInorder(root.left, list);
        list.add(root.val);
        fixedInorder(root.right, list);
    }

    // Bug 3: 刪除單子節點時遺失子項目 (直接回傳 null)
    static Node buggyDelete(Node root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            root.left = buggyDelete(root.left, val);
        } else if (val > root.val) {
            root.right = buggyDelete(root.right, val);
        } else {
            if (root.left == null && root.right != null) {
                return null; // BUG: 遺失右子樹

            }
            return null;
        }
        return root;
    }

    static Node fixedDelete(Node root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            root.left = fixedDelete(root.left, val);
        } else if (val > root.val) {
            root.right = fixedDelete(root.right, val);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            Node min = root.right;
            while (min.left != null) {
                min = min.left;
            }
            root.val = min.val;
            root.right = fixedDelete(root.right, min.val);
        }
        return root;
    }

    // Bug 4: 驗證 BST 僅檢查直接子節點
    static boolean buggyIsValidBST(Node root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.left.val >= root.val) {
            return false;
        }
        if (root.right != null && root.right.val <= root.val) {
            return false;
        }
        return buggyIsValidBST(root.left) && buggyIsValidBST(root.right);
    }

    static boolean fixedIsValidBST(Node root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        return fixedIsValidBST(root.left, min, root.val) && fixedIsValidBST(root.right, root.val, max);
    }

    public static void main(String[] args) {
        System.out.println("=== Bug 1: 搜尋方向錯誤展示 ===");
        Node bst1 = new Node(10);
        bst1.left = new Node(5);
        System.out.println("搜尋 5 (Buggy): " + buggySearch(bst1, 5) + " | (Fixed): " + fixedSearch(bst1, 5));

        System.out.println("\n=== Bug 2: 中序走訪錯誤展示 ===");
        List<Integer> b2 = new ArrayList<>(), f2 = new ArrayList<>();
        buggyInorder(bst1, b2);
        fixedInorder(bst1, f2);
        System.out.println("中序輸出 (Buggy): " + b2 + " | (Fixed): " + f2);

        System.out.println("\n=== Bug 3: 刪除單子節點遺失展示 ===");
        Node bst3 = new Node(10);
        bst3.right = new Node(20);
        Node bugDel = buggyDelete(bst3, 10);
        Node fixDel = fixedDelete(new Node(10) {
            {
                right = new Node(20);
            }
        }, 10);
        System.out.println("刪除根節點 10 後右子節點 (Buggy): " + (bugDel == null ? "null (遺失)" : bugDel.val)
                + " | (Fixed): " + (fixDel == null ? "null" : fixDel.val));

        System.out.println("\n=== Bug 4: BST 局部驗證漏洞展示 ===");
        // 樹結構: 10 -> (left: 5), (right: 15 -> left: 6) -> 6 小於 10，違反 BST
        Node invalidTree = new Node(10);
        invalidTree.left = new Node(5);
        invalidTree.right = new Node(15);
        invalidTree.right.left = new Node(6);
        System.out.println("驗證違規樹 (Buggy): " + buggyIsValidBST(invalidTree) + " | (Fixed): " + fixedIsValidBST(invalidTree, Long.MIN_VALUE, Long.MAX_VALUE));
    }
}
