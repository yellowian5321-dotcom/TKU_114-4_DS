
import java.util.ArrayList;
import java.util.List;

public class OrderManagementBst {

    public enum OrderStatus {
        PENDING, PAID, SHIPPED, CANCELED
    }

    static class Order {

        int orderId;
        String customer;
        double amount;
        OrderStatus status;

        Order(int orderId, String customer, double amount, OrderStatus status) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Order[ID=%d, Customer='%s', Amount=%.2f, Status=%s]", orderId, customer, amount, status);
        }
    }

    static class Node {

        Order order;
        Node left, right;

        Node(Order order) {
            this.order = order;
        }
    }

    private Node root;

    public boolean insert(int orderId, String customer, double amount, OrderStatus status) {
        if (amount < 0) {
            System.out.println("新增失敗: 訂單金額不得為負數！");
            return false;
        }
        if (find(orderId) != null) {
            System.out.println("新增失敗: 訂單 ID " + orderId + " 已存在！");
            return false;
        }
        root = insertRec(root, new Order(orderId, customer, amount, status));
        return true;
    }

    private Node insertRec(Node cur, Order order) {
        if (cur == null) {
            return new Node(order);
        }
        if (order.orderId < cur.order.orderId) {
            cur.left = insertRec(cur.left, order); 
        }else {
            cur.right = insertRec(cur.right, order);
        }
        return cur;
    }

    public Order find(int orderId) {
        Node cur = root;
        while (cur != null) {
            if (orderId == cur.order.orderId) {
                return cur.order;
            }
            cur = (orderId < cur.order.orderId) ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateStatus(int orderId, OrderStatus newStatus) {
        Order o = find(orderId);
        if (o == null) {
            return false;
        }
        o.status = newStatus;
        return true;
    }

    public boolean cancel(int orderId) {
        return updateStatus(orderId, OrderStatus.CANCELED);
    }

    public boolean remove(int orderId) {
        Order o = find(orderId);
        if (o == null) {
            System.out.println("刪除失敗: 查無訂單 " + orderId);
            return false;
        }
        if (o.status != OrderStatus.CANCELED) {
            System.out.println("刪除失敗: 訂單狀態為 " + o.status + "，僅 CANCELED 狀態可刪除！");
            return false;
        }
        root = deleteRec(root, orderId);
        return true;
    }

    private Node deleteRec(Node cur, int orderId) {
        if (cur == null) {
            return null;
        }
        if (orderId < cur.order.orderId) {
            cur.left = deleteRec(cur.left, orderId); 
        }else if (orderId > cur.order.orderId) {
            cur.right = deleteRec(cur.right, orderId); 
        }else {
            if (cur.left == null) {
                return cur.right;
            }
            if (cur.right == null) {
                return cur.left;
            }
            Node min = findMin(cur.right);
            cur.order = min.order;
            cur.right = deleteRec(cur.right, min.order.orderId);
        }
        return cur;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Order> findByIdRange(int lowId, int highId) {
        List<Order> list = new ArrayList<>();
        rangeRec(root, lowId, highId, list);
        return list;
    }

    private void rangeRec(Node cur, int low, int high, List<Order> list) {
        if (cur == null) {
            return;
        }
        if (cur.order.orderId > low) {
            rangeRec(cur.left, low, high, list);
        }
        if (cur.order.orderId >= low && cur.order.orderId <= high) {
            list.add(cur.order);
        }
        if (cur.order.orderId < high) {
            rangeRec(cur.right, low, high, list);
        }
    }

    public double calculateTotalAmount() {
        return sumAmountRec(root);
    }

    private double sumAmountRec(Node n) {
        if (n == null) {
            return 0.0;
        }
        return n.order.amount + sumAmountRec(n.left) + sumAmountRec(n.right);
    }

    public void printReport() {
        System.out.println("--- 訂單系統報表 ---");
        inorder(root);
        System.out.printf("系統全部訂單總金額: $%.2f%n", calculateTotalAmount());
        System.out.println("--------------------");
    }

    private void inorder(Node n) {
        if (n == null) {
            return;
        }
        inorder(n.left);
        System.out.println(n.order);
        inorder(n.right);
    }

    public static void main(String[] args) {
        OrderManagementBst manager = new OrderManagementBst();
        manager.insert(1003, "Alice", 1500.0, OrderStatus.PAID);
        manager.insert(1001, "Bob", 450.0, OrderStatus.PENDING);
        manager.insert(1005, "Charlie", 3200.0, OrderStatus.SHIPPED);
        manager.insert(1002, "David", -50.0, OrderStatus.PENDING); // 失敗 (負金額)

        manager.printReport();
        manager.remove(1001); // 失敗 (狀態為 PENDING)
        manager.cancel(1001); // 改為 CANCELED
        manager.remove(1001); // 成功刪除
        manager.printReport();
    }
}
