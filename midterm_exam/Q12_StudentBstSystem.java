
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {

        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0 || name == null || name.isBlank()) {
                throw new IllegalArgumentException("id must be > 0 and name cannot be blank");
            }
            this.id = id;
            this.name = name;
            this.score = Math.max(0, Math.min(100, score));
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = Math.max(0, Math.min(100, score));
        }

        @Override
        public String toString() {
            return id + " | " + name + " | " + score;
        }
    }

    private static class Node {

        Student student;
        Node left, right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null || find(student.getId()) != null) {
            return false;
        }
        root = addHelper(root, student);
        return true;
    }

    private Node addHelper(Node node, Student student) {
        if (node == null) {
            return new Node(student);
        }
        if (student.getId() < node.student.getId()) {
            node.left = addHelper(node.left, student); 
        }else if (student.getId() > node.student.getId()) {
            node.right = addHelper(node.right, student);
        }
        return node;
    }

    public Student find(int id) {
        Node curr = root;
        while (curr != null) {
            if (id == curr.student.getId()) {
                return curr.student;
            }
            curr = (id < curr.student.getId()) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student s = find(id);
        if (s == null) {
            return false;
        }
        s.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = removeHelper(root, id);
        return true;
    }

    private Node removeHelper(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.student.getId()) {
            node.left = removeHelper(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = removeHelper(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            Node minNode = findMin(node.right);
            node.student = minNode.student;
            node.right = removeHelper(node.right, minNode.student.getId());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        if (lowId > highId) {
            return Collections.emptyList();
        }
        List<Student> list = new ArrayList<>();
        rangeHelper(root, lowId, highId, list);
        return list;
    }

    private void rangeHelper(Node node, int low, int high, List<Student> list) {
        if (node == null) {
            return;
        }
        if (node.student.getId() > low) {
            rangeHelper(node.left, low, high, list);
        }
        if (node.student.getId() >= low && node.student.getId() <= high) {
            list.add(node.student);
        }
        if (node.student.getId() < high) {
            rangeHelper(node.right, low, high, list);
        }
    }

    public List<Student> inorder() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Student> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    private void inorderHelper(Node node, List<Student> list) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, list);
        list.add(node.student);
        inorderHelper(node.right, list);
    }
}
