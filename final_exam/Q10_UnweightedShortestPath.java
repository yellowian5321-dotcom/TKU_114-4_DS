
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return new ArrayList<>();
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return new ArrayList<>();
        }
        if (start.equals(target)) {
            List<String> single = new ArrayList<>();
            single.add(start);
            return single;
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> predecessor = new HashMap<>();

        queue.offer(start);
        predecessor.put(start, null);

        boolean reached = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                reached = true;
                break;
            }

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String next : neighbors) {
                    if (next != null && !predecessor.containsKey(next)) {
                        predecessor.put(next, curr);
                        queue.offer(next);
                        if (next.equals(target)) {
                            reached = true;
                            break;
                        }
                    }
                }
            }
            if (reached) {
                break;
            }
        }

        if (!predecessor.containsKey(target)) {
            return new ArrayList<>();
        }

        List<String> path = new ArrayList<>();
        String step = target;
        while (step != null) {
            path.add(step);
            step = predecessor.get(step);
        }
        Collections.reverse(path);
        return path;
    }
}
