
import java.util.ArrayList;
import java.util.List;

public class CourseBstIndex {

    static class Course {

        String courseCode; // Key
        String title;
        int credits; // 1 ~ 6

        Course(String courseCode, String title, int credits) {
            this.courseCode = courseCode;
            this.title = title;
            this.credits = credits;
        }

        @Override
        public String toString() {
            return String.format("Course[Code='%s', Title='%s', Credits=%d]", courseCode, title, credits);
        }
    }

    static class Node {

        Course course;
        Node left, right;

        Node(Course course) {
            this.course = course;
        }
    }

    private Node root;

    public boolean insert(String code, String title, int credits) {
        if (credits < 1 || credits > 6) {
            System.out.println("新增失敗: 學分限制必須在 1 到 6 之間！");
            return false;
        }
        if (find(code) != null) {
            System.out.println("新增失敗: 課程代碼 " + code + " 已存在！");
            return false;
        }
        root = insertRec(root, new Course(code, title, credits));
        return true;
    }

    private Node insertRec(Node current, Course course) {
        if (current == null) {
            return new Node(course);
        }
        int cmp = course.courseCode.compareTo(current.course.courseCode);
        if (cmp < 0) {
            current.left = insertRec(current.left, course); 
        }else {
            current.right = insertRec(current.right, course);
        }
        return current;
    }

    public Course find(String code) {
        Node cur = root;
        while (cur != null) {
            int cmp = code.compareTo(cur.course.courseCode);
            if (cmp == 0) {
                return cur.course;
            }
            cur = (cmp < 0) ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateCredits(String code, int newCredits) {
        if (newCredits < 1 || newCredits > 6) {
            System.out.println("更新失敗: 學分限制必須在 1 到 6 之間！");
            return false;
        }
        Course c = find(code);
        if (c == null) {
            return false;
        }
        c.credits = newCredits;
        return true;
    }

    public boolean delete(String code) {
        if (find(code) == null) {
            return false;
        }
        root = deleteRec(root, code);
        return true;
    }

    private Node deleteRec(Node current, String code) {
        if (current == null) {
            return null;
        }
        int cmp = code.compareTo(current.course.courseCode);
        if (cmp < 0) {
            current.left = deleteRec(current.left, code); 
        }else if (cmp > 0) {
            current.right = deleteRec(current.right, code); 
        }else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node min = findMin(current.right);
            current.course = min.course;
            current.right = deleteRec(current.right, min.course.courseCode);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Course> findByCodeRange(String lowCode, String highCode) {
        List<Course> list = new ArrayList<>();
        rangeRec(root, lowCode, highCode, list);
        return list;
    }

    private void rangeRec(Node node, String low, String high, List<Course> list) {
        if (node == null) {
            return;
        }
        if (node.course.courseCode.compareTo(low) > 0) {
            rangeRec(node.left, low, high, list);
        }
        if (node.course.courseCode.compareTo(low) >= 0 && node.course.courseCode.compareTo(high) <= 0) {
            list.add(node.course);
        }
        if (node.course.courseCode.compareTo(high) < 0) {
            rangeRec(node.right, low, high, list);
        }
    }

    public void printSortedReport() {
        System.out.println("--- 課程清單報表 (依代碼排序) ---");
        inorder(root);
    }

    private void inorder(Node n) {
        if (n == null) {
            return;
        }
        inorder(n.left);
        System.out.println(n.course);
        inorder(n.right);
    }

    public static void main(String[] args) {
        CourseBstIndex index = new CourseBstIndex();
        index.insert("CS101", "計算機概論", 3);
        index.insert("CS201", "資料結構", 4);
        index.insert("CS301", "演算法", 3);
        index.insert("MATH101", "微積分", 4);
        index.insert("CS101", "重複課", 2); // 失敗
        index.insert("CS999", "無效學分課", 8); // 失敗

        index.printSortedReport();
        index.updateCredits("CS101", 4);
        System.out.println("\n查詢範圍 [CS150, CS350]: " + index.findByCodeRange("CS150", "CS350"));
        index.delete("CS201");
        index.printSortedReport();
    }
}
