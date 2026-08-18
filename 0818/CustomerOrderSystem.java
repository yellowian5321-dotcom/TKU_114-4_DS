
class Customer {

    private String customerId;
    private String name;
    private String phone;

    public Customer(String customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return name + " (ID: " + customerId + ", 電話: " + phone + ")";
    }
}

class OrderItem {

    private String itemName;
    private double unitPrice;
    private int quantity;

    public OrderItem(String itemName, double unitPrice, int quantity) {
        this.itemName = itemName;
        this.unitPrice = Math.max(unitPrice, 0.0);
        this.quantity = Math.max(quantity, 0);
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("品項: %-10s | 單價: %6.1f | 數量: %2d | 小計: %8.1f",
                itemName, unitPrice, quantity, getSubtotal());
    }
}

class Order {

    private String orderId;
    private Customer customer;
    private OrderItem[] items;
    private int itemCount;

    public Order(String orderId, Customer customer, int capacity) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[Math.max(capacity, 1)];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (item == null) {
            return false;
        }
        if (itemCount < items.length) {
            items[itemCount++] = item;
            return true;
        }
        System.out.println("【訂單已滿】無法再新增品項。");
        return false;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public int getTotalQuantity() {
        int count = 0;
        for (int i = 0; i < itemCount; i++) {
            count += items[i].getQuantity();
        }
        return count;
    }

    public void printSummary() {
        System.out.println("==================================================");
        System.out.println("訂單編號: " + orderId);
        System.out.println("訂購顧客: " + customer);
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < itemCount; i++) {
            System.out.println(" " + (i + 1) + ". " + items[i]);
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("品項種類數: %d | 總商品件數: %d | 訂單總額: %.2f 元\n",
                itemCount, getTotalQuantity(), calculateTotal());
        System.out.println("==================================================");
    }
}

public class CustomerOrderSystem {

    public static void main(String[] args) {
        Customer cust = new Customer("C001", "陳雨紅", "0912-345-678");
        Order order = new Order("ORD-2026-001", cust, 3);

        order.addItem(new OrderItem("機械鍵盤", 2490.0, 1));
        order.addItem(new OrderItem("電競滑鼠", 1290.0, 2));
        order.addItem(new OrderItem("大滑鼠墊", 450.0, 1));

        // 嘗試加入超過容量之品項
        order.addItem(new OrderItem("USB 隨身碟", 300.0, 1));

        order.printSummary();
    }
}
