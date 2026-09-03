
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q08_BfsTraversal {

    public static List<String> bfs(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new ArrayList<>();
        }

        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            order.add(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String next : neighbors) {
                    if (next != null && visited.add(next)) {
                        queue.offer(next);
                    }
                }
            }
        }
        return order;
    }

    public static Map<String, Integer> distanceFrom(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new HashMap<>();
        }

        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        dist.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currentDist = dist.get(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String next : neighbors) {
                    if (next != null && !dist.containsKey(next)) {
                        dist.put(next, currentDist + 1);
                        queue.offer(next);
                    }
                }
            }
        }
        return dist;
    }
}
