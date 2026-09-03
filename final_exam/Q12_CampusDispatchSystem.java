
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {

    public record Request(String id, String location, int priority, long sequence) {

    }

    private final Map<String, List<String>> roads = new HashMap<>();
    private final Map<String, Request> requestsById = new HashMap<>();
    private final PriorityQueue<Request> pq;

    public Q12_CampusDispatchSystem() {
        Comparator<Request> comparator = Comparator
                .comparingInt(Request::priority)
                .thenComparingLong(Request::sequence);
        this.pq = new PriorityQueue<>(comparator);
    }

    public boolean addLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }
        if (roads.containsKey(location)) {
            return false;
        }
        roads.put(location, new ArrayList<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        if (!roads.containsKey(first) || !roads.containsKey(second)) {
            return false;
        }

        List<String> adjFirst = roads.get(first);
        if (adjFirst.contains(second)) {
            return false;
        }

        adjFirst.add(second);
        roads.get(second).add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }
        if (!roads.containsKey(request.location())) {
            return false;
        }
        if (requestsById.containsKey(request.id())) {
            return false;
        }

        requestsById.put(request.id(), request);
        pq.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !roads.containsKey(serviceCenter)) {
            return null;
        }

        Set<String> reachableNodes = getReachableSet(serviceCenter);
        List<Request> unreachableTemp = new ArrayList<>();
        Request matched = null;

        while (!pq.isEmpty()) {
            Request candidate = pq.poll();
            if (reachableNodes.contains(candidate.location())) {
                matched = candidate;
                break;
            } else {
                unreachableTemp.add(candidate);
            }
        }

        pq.addAll(unreachableTemp);
        if (matched != null) {
            requestsById.remove(matched.id());
        }
        return matched;
    }

    private Set<String> getReachableSet(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            List<String> neighbors = roads.get(curr);
            if (neighbors != null) {
                for (String next : neighbors) {
                    if (visited.add(next)) {
                        queue.offer(next);
                    }
                }
            }
        }
        return visited;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null) {
            return new ArrayList<>();
        }
        if (!roads.containsKey(start) || !roads.containsKey(target)) {
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

        boolean found = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                found = true;
                break;
            }
            List<String> neighbors = roads.get(curr);
            if (neighbors != null) {
                for (String next : neighbors) {
                    if (!predecessor.containsKey(next)) {
                        predecessor.put(next, curr);
                        queue.offer(next);
                        if (next.equals(target)) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            if (found) {
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

    public int pendingCount() {
        return pq.size();
    }
}
