
import java.util.*;

public class LoginActivityReport {

    private final Map<String, Integer> loginCounts = new HashMap<>();
    private final Map<String, Set<String>> userIps = new HashMap<>();

    public void recordLogin(String username, String ip) {
        loginCounts.put(username, loginCounts.getOrDefault(username, 0) + 1);
        userIps.computeIfAbsent(username, k -> new HashSet<>()).add(ip);
    }

    public void printSecurityReport(int suspiciousLoginThreshold, int suspiciousIpThreshold) {
        System.out.println("================ 登入活動與安全性分析報告 ================");
        for (String user : loginCounts.keySet()) {
            int count = loginCounts.get(user);
            Set<String> ips = userIps.get(user);
            boolean isSuspicious = count >= suspiciousLoginThreshold || ips.size() >= suspiciousIpThreshold;

            System.out.printf("帳號: %-10s | 登入次數: %3d | 獨立 IP 數: %2d %s%n",
                    user, count, ips.size(), isSuspicious ? " [!] 警告：異常頻繁/多地登入" : "");
            System.out.println("  使用過的 IP 列表: " + ips);
        }
        System.out.println("=======================================================");
    }

    public static void main(String[] args) {
        LoginActivityReport report = new LoginActivityReport();
        report.recordLogin("admin", "192.168.1.1");
        report.recordLogin("admin", "192.168.1.2");
        report.recordLogin("admin", "10.0.0.5");
        report.recordLogin("admin", "172.16.0.8");
        report.recordLogin("user1", "192.168.1.10");
        report.recordLogin("user1", "192.168.1.10");

        report.printSecurityReport(3, 3);
    }
}
