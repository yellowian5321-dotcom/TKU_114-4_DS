
import java.util.*;

public class DeliveryWorkflowSystem {

    static class PackageOrder {

        String trackingId;
        String destination;

        public PackageOrder(String trackingId, String destination) {
            this.trackingId = trackingId;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "包裹{" + trackingId + ", 目的地: " + destination + "}";
        }
    }

    private final Map<String, PackageOrder> allOrders = new HashMap<>();
    private final Queue<PackageOrder> waitingQueue = new LinkedList<>();
    private final Deque<PackageOrder> completedStack = new ArrayDeque<>();

    public boolean addOrder(String id, String dest) {
        if (allOrders.containsKey(id)) {
            System.out.println("新增失敗：包裹編號 " + id + " 已存在！");
            return false;
        }
        PackageOrder order = new PackageOrder(id, dest);
        allOrders.put(id, order);
        waitingQueue.offer(order);
        System.out.println("新增包裹: " + order);
        return true;
    }

    public void processNext() {
        PackageOrder order = waitingQueue.poll();
        if (order == null) {
            System.out.println("處理失敗：無等待配送的包裹。");
            return;
        }
        completedStack.push(order);
        System.out.println("已配送完成: " + order);
    }

    public void undoDelivery() {
        if (completedStack.isEmpty()) {
            System.out.println("撤銷失敗：尚無已完成的包裹流程。");
            return;
        }
        PackageOrder rollbackOrder = completedStack.pop();
        // 撤銷時放回等待隊列
        ((LinkedList<PackageOrder>) waitingQueue).addFirst(rollbackOrder);
        System.out.println("已撤銷配送並放回隊列前端: " + rollbackOrder);
    }

    public void queryOrder(String id) {
        PackageOrder order = allOrders.get(id);
        System.out.println("查詢 [" + id + "]: " + (order != null ? order : "查無此包裹"));
    }

    public void printStats() {
        System.out.println("【統計】總包裹數: " + allOrders.size()
                + ", 等待中: " + waitingQueue.size()
                + ", 已完成: " + completedStack.size());
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem system = new DeliveryWorkflowSystem();
        system.addOrder("TRK001", "台北市");
        system.addOrder("TRK002", "新北市");
        system.addOrder("TRK001", "重複測試"); // 重複 id 測試
        system.processNext();
        system.printStats();
        system.undoDelivery();
        system.printStats();
        system.queryOrder("TRK001");
        system.queryOrder("TRK999");
    }
}
