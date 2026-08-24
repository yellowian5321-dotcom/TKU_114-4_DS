
import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserBackStack {

    private final Deque<String> history = new ArrayDeque<>();
    private String currentPage = null;

    public void visit(String url) {
        if (currentPage != null) {
            history.push(currentPage);
        }
        currentPage = url;
        System.out.println("造訪頁面: " + currentPage);
    }

    public void back() {
        if (history.isEmpty()) {
            System.out.println("已無上一頁可返回，維持在: " + currentPage);
            return;
        }
        currentPage = history.pop();
        System.out.println("返回上一頁: " + currentPage);
    }

    public void current() {
        System.out.println("目前頁面: " + (currentPage != null ? currentPage : "無"));
    }

    public static void main(String[] args) {
        BrowserBackStack browser = new BrowserBackStack();
        browser.back();                     // 1. 空棧返回測試
        browser.visit("https://google.com"); // 2. 造訪
        browser.visit("https://github.com"); // 3. 造訪
        browser.current();                  // 4. 目前頁面
        browser.visit("https://openai.com"); // 5. 造訪
        browser.back();                     // 6. 返回
        browser.back();                     // 7. 返回
        browser.back();                     // 8. 返回至底
        browser.back();                     // 9. 超出返回測試
    }
}
