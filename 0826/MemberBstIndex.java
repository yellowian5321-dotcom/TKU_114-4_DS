
public class MemberBstIndex {

    static class Member {

        int memberId;
        String name;
        String email;

        Member(int memberId, String name, String email) {
            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("Member[ID=%d, Name='%s', Email='%s']", memberId, name, email);
        }
    }

    static class Node {

        Member member;
        Node left, right;

        Node(Member member) {
            this.member = member;
        }
    }

    private Node root;

    public boolean insert(int memberId, String name, String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("新增失敗: Email 不得為空白！");
            return false;
        }
        if (find(memberId) != null) {
            System.out.println("新增失敗: Member ID " + memberId + " 已存在！");
            return false;
        }
        root = insertRec(root, new Member(memberId, name, email));
        return true;
    }

    private Node insertRec(Node current, Member member) {
        if (current == null) {
            return new Node(member);
        }
        if (member.memberId < current.member.memberId) {
            current.left = insertRec(current.left, member); 
        }else {
            current.right = insertRec(current.right, member);
        }
        return current;
    }

    public Member find(int memberId) {
        Node cur = root;
        while (cur != null) {
            if (memberId == cur.member.memberId) {
                return cur.member;
            }
            cur = (memberId < cur.member.memberId) ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateEmail(int memberId, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            System.out.println("更新失敗: Email 不得為空白！");
            return false;
        }
        Member m = find(memberId);
        if (m == null) {
            System.out.println("更新失敗: 查無 Member ID " + memberId);
            return false;
        }
        m.email = newEmail;
        return true;
    }

    public boolean delete(int memberId) {
        if (find(memberId) == null) {
            return false;
        }
        root = deleteRec(root, memberId);
        return true;
    }

    private Node deleteRec(Node current, int memberId) {
        if (current == null) {
            return null;
        }
        if (memberId < current.member.memberId) {
            current.left = deleteRec(current.left, memberId); 
        }else if (memberId > current.member.memberId) {
            current.right = deleteRec(current.right, memberId); 
        }else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node min = findMin(current.right);
            current.member = min.member;
            current.right = deleteRec(current.right, min.member.memberId);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void printInorderReport() {
        System.out.println("--- 會員索引排序報表 ---");
        inorderRec(root);
        System.out.println("------------------------");
    }

    private void inorderRec(Node n) {
        if (n == null) {
            return;
        }
        inorderRec(n.left);
        System.out.println(n.member);
        inorderRec(n.right);
    }

    public static void main(String[] args) {
        MemberBstIndex index = new MemberBstIndex();
        index.insert(103, "Alice", "alice@example.com");
        index.insert(101, "Bob", "bob@example.com");
        index.insert(105, "Charlie", "charlie@example.com");
        index.insert(101, "Duplicate Bob", "dup@example.com"); // 失敗
        index.insert(108, "David", "   "); // 失敗

        index.printInorderReport();
        index.updateEmail(101, "bob_new@example.com");
        System.out.println("查詢 101: " + index.find(101));

        index.delete(103);
        index.printInorderReport();
    }
}
