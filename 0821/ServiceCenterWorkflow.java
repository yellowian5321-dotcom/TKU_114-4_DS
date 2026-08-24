
import java.util.*;

public class ServiceCenterWorkflow {

    static class ServiceTicket {

        String ticketId;
        String description;

        public ServiceTicket(String ticketId, String description) {
            this.ticketId = ticketId;
            this.description = description;
        }

        @Override
        public String toString() {
            return "[" + ticketId + ": " + description + "]";
        }
    }

    private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();

    public boolean createTicket(String ticketId, String description) {
        if (!registeredIds.add(ticketId)) {
            System.out.println("建立失敗：工單編號 " + ticketId + " 重複！");
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(ticketId, description);
        ticketMap.put(ticketId, ticket);
        waitingQueue.offerLast(ticket);
        System.out.println("工單建立成功: " + ticket);
        return true;
    }

    public ServiceTicket processNext() {
        ServiceTicket ticket = waitingQueue.pollFirst();
        if (ticket == null) {
            System.out.println("處理失敗：等待隊列為空。");
            return null;
        }
        completedStack.push(ticket);
        System.out.println("已完成處理工單: " + ticket);
        return ticket;
    }

    public boolean cancelWaiting(String ticketId) {
        if (!ticketMap.containsKey(ticketId)) {
            System.out.println("取消失敗：工單 " + ticketId + " 不存在。");
            return false;
        }
        boolean removed = waitingQueue.removeIf(t -> t.ticketId.equals(ticketId));
        if (removed) {
            ticketMap.remove(ticketId);
            registeredIds.remove(ticketId);
            System.out.println("已成功取消等待中工單: " + ticketId);
            return true;
        } else {
            System.out.println("取消失敗：工單 " + ticketId + " 已處理或不在等待隊列中。");
            return false;
        }
    }

    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("撤銷失敗：無已完成的工單可撤銷。");
            return false;
        }
        ServiceTicket ticket = completedStack.pop();
        waitingQueue.offerFirst(ticket); // 放回等待隊列前端
        System.out.println("撤銷完成：工單 " + ticket + " 已回退至等待佇列最前端。");
        return true;
    }

    public ServiceTicket findById(String ticketId) {
        return ticketMap.get(ticketId);
    }

    public void printSummary() {
        System.out.println("【服務中心狀態總覽】");
        System.out.println(" - 等待隊列: " + waitingQueue);
        System.out.println(" - 完成堆疊: " + completedStack);
        System.out.println(" - 總登記數: " + registeredIds.size());
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("--- 測試 1: 建立工單與重複 ID ---");
        center.createTicket("TK-01", "網路連線異常");
        center.createTicket("TK-02", "帳號被鎖定");
        center.createTicket("TK-03", "硬體損壞更換");
        center.createTicket("TK-01", "重複 ID 測試");

        System.out.println("\n--- 測試 2: 取消不存在 ID 與 取消等待中工單 ---");
        center.cancelWaiting("TK-99");
        center.cancelWaiting("TK-02");

        System.out.println("\n--- 測試 3: 處理工單與空隊列 ---");
        center.processNext(); // 處理 TK-01
        center.processNext(); // 處理 TK-03
        center.processNext(); // 空隊列處理

        System.out.println("\n--- 測試 4: 連續兩次撤銷 ---");
        center.undoLastCompletion(); // 撤銷 TK-03
        center.undoLastCompletion(); // 撤銷 TK-01
        center.undoLastCompletion(); // 無可撤銷測試

        System.out.println("\n--- 最終狀態 ---");
        center.printSummary();
    }
}
