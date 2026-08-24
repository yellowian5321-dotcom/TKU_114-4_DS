
import java.util.ArrayDeque;
import java.util.Deque;

public class CounterWaitingQueue {

    static class Customer {

        String id;
        String name;

        public Customer(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "[" + id + " - " + name + "]";
        }
    }

    private final Deque<Customer> queue = new ArrayDeque<>();

    public void addCustomer(Customer c) {
        queue.offerLast(c);
        System.out.println("顧客加入: " + c + "，目前等候人數: " + queue.size());
    }

    public void peekNext() {
        Customer next = queue.peekFirst();
        if (next == null) {
            System.out.println("查看失敗：目前隊列為空。");
        } else {
            System.out.println("下一位等候顧客: " + next);
        }
    }

    public void serveNext() {
        Customer served = queue.pollFirst();
        if (served == null) {
            System.out.println("服務失敗：無顧客等待中。");
        } else {
            System.out.println("正在服務: " + served + "，剩餘等候人數: " + queue.size());
        }
    }

    public static void main(String[] args) {
        CounterWaitingQueue counter = new CounterWaitingQueue();
        counter.serveNext(); // 空隊列測試
        counter.addCustomer(new Customer("C001", "張三"));
        counter.addCustomer(new Customer("C002", "李四"));
        counter.peekNext();
        counter.serveNext();
        counter.serveNext();
        counter.serveNext(); // 再次空隊列測試
    }
}
