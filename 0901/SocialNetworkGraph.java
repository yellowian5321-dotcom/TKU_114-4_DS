
import java.util.*;

public class SocialNetworkGraph {

    private final Map<String, Set<String>> network = new HashMap<>();

    public void addUser(String user) {
        network.putIfAbsent(user, new HashSet<>());
    }

    public void addFriendship(String u1, String u2) {
        if (u1.equals(u2)) {
            return;
        }
        addUser(u1);
        addUser(u2);
        network.get(u1).add(u2);
        network.get(u2).add(u1);
    }

    public void removeFriendship(String u1, String u2) {
        if (network.containsKey(u1)) {
            network.get(u1).remove(u2);
        }
        if (network.containsKey(u2)) {
            network.get(u2).remove(u1);
        }
    }

    public Set<String> getMutualFriends(String u1, String u2) {
        if (!network.containsKey(u1) || !network.containsKey(u2)) {
            return Collections.emptySet();
        }
        Set<String> mutual = new HashSet<>(network.get(u1));
        mutual.retainAll(network.get(u2));
        return mutual;
    }

    public List<String> getIsolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : network.entrySet()) {
            if (entry.getValue().isEmpty()) {
                isolated.add(entry.getKey());
            }
        }
        return isolated;
    }

    public void printNetwork() {
        System.out.println("========== 社群網路狀況 ==========");
        network.forEach((user, friends)
                -> System.out.printf("用戶: %-8s | 好友數: %2d | 好友: %s%n", user, friends.size(), friends)
        );
        System.out.println("孤立用戶: " + getIsolatedUsers());
    }

    public static void main(String[] args) {
        SocialNetworkGraph sn = new SocialNetworkGraph();
        sn.addFriendship("Alice", "Bob");
        sn.addFriendship("Alice", "Charlie");
        sn.addFriendship("Bob", "Charlie");
        sn.addFriendship("Bob", "David");
        sn.addUser("Eve"); // 孤立用戶

        sn.printNetwork();
        System.out.println("Alice 與 Bob 的共同好友: " + sn.getMutualFriends("Alice", "Bob"));
    }
}
