
import java.util.*;

public class LogisticsWeightedGraph {

    // 鄰接表結構：起點 -> (終點 -> 權重/成本)
    private final Map<String, Map<String, Double>> adj = new HashMap<>();

    public void addHub(String hubName) {
        adj.putIfAbsent(hubName, new HashMap<>());
    }

    public void setRoute(String src, String dest, double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("成本/權重不能為負數: " + cost);
        }
        if (!adj.containsKey(src) || !adj.containsKey(dest)) {
            throw new NoSuchElementException("站點不存在，請先新增物流節點");
        }
        adj.get(src).put(dest, cost);
    }

    public boolean removeRoute(String src, String dest) {
        if (adj.containsKey(src)) {
            return adj.get(src).remove(dest) != null;
        }
        return false;
    }

    public Double getRouteCost(String src, String dest) {
        if (adj.containsKey(src)) {
            return adj.get(src).get(dest);
        }
        return null;
    }

    public void printNetwork() {
        System.out.println("================ 物流成本網路報告 ================");
        for (var entry : adj.entrySet()) {
            String src = entry.getKey();
            Map<String, Double> routes = entry.getValue();
            System.out.printf("發貨中心: %-8s | 可達路線數: %d%n", src, routes.size());
            routes.forEach((dest, cost)
                    -> System.out.printf("  └──> 目的地: %-8s | 運輸成本: %.2f%n", dest, cost)
            );
        }
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph logistics = new LogisticsWeightedGraph();
        logistics.addHub("台北轉運");
        logistics.addHub("台中轉運");
        logistics.addHub("台南轉運");
        logistics.addHub("高雄轉運");

        logistics.setRoute("台北轉運", "台中轉運", 120.5);
        logistics.setRoute("台中轉運", "台南轉運", 95.0);
        logistics.setRoute("台南轉運", "高雄轉運", 60.0);
        logistics.setRoute("台北轉運", "高雄轉運", 260.0);

        logistics.printNetwork();
    }
}
