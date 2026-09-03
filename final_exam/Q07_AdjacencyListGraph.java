
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q07_AdjacencyListGraph {

    private final Map<String, Set<String>> adj = new HashMap<>();
    private int edgeCount = 0;

    public boolean addVertex(String vertex) {
        if (vertex == null || adj.containsKey(vertex)) {
            return false;
        }
        adj.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) {
            return false;
        }
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            return false;
        }

        Set<String> neighbors = adj.get(from);
        if (neighbors.contains(to)) {
            return false;
        }

        neighbors.add(to);
        edgeCount++;
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) {
            return false;
        }
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            return false;
        }

        Set<String> neighbors = adj.get(from);
        if (neighbors.remove(to)) {
            edgeCount--;
            return true;
        }
        return false;
    }

    public List<String> outgoing(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adj.get(vertex));
    }

    public int inDegree(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return 0;
        }
        int inDeg = 0;
        for (Set<String> edges : adj.values()) {
            if (edges.contains(vertex)) {
                inDeg++;
            }
        }
        return inDeg;
    }

    public int edgeCount() {
        return edgeCount;
    }
}
