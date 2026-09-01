
import java.util.*;

public class WebsiteLinkGraph {

    private final Map<String, Set<String>> outgoingLinks = new HashMap<>();
    private final Map<String, Set<String>> incomingLinks = new HashMap<>();

    public void addPage(String url) {
        outgoingLinks.putIfAbsent(url, new HashSet<>());
        incomingLinks.putIfAbsent(url, new HashSet<>());
    }

    public void addLink(String fromUrl, String toUrl) {
        addPage(fromUrl);
        addPage(toUrl);
        outgoingLinks.get(fromUrl).add(toUrl);
        incomingLinks.get(toUrl).add(fromUrl);
    }

    public void printWebAnalysis() {
        System.out.println("================ 網站連結網路分析 ================");
        List<String> pages = new ArrayList<>(outgoingLinks.keySet());
        Collections.sort(pages);

        List<String> noInbound = new ArrayList<>();
        List<String> noOutbound = new ArrayList<>();

        for (String p : pages) {
            int outCount = outgoingLinks.get(p).size();
            int inCount = incomingLinks.get(p).size();
            if (inCount == 0) {
                noInbound.add(p);
            }
            if (outCount == 0) {
                noOutbound.add(p);
            }

            System.out.printf("頁面: %-20s | 傳出數: %2d | 傳入數: %2d | 外連清單: %s%n",
                    p, outCount, inCount, outgoingLinks.get(p));
        }

        System.out.println("\n【關鍵結構診斷】");
        System.out.println("無傳入頁面 (入口/孤島頁面): " + noInbound);
        System.out.println("無傳出頁面 (終點頁面): " + noOutbound);
    }

    public static void main(String[] args) {
        WebsiteLinkGraph web = new WebsiteLinkGraph();
        web.addLink("index.html", "about.html");
        web.addLink("index.html", "products.html");
        web.addLink("products.html", "detail.html");
        web.addLink("detail.html", "index.html");
        web.addPage("isolated.html");

        web.printWebAnalysis();
    }
}
