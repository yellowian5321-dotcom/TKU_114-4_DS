
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {

    private final List<String> vertexList;
    private final Map<String, Integer> vertexIndices;
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertexList = new ArrayList<>();
        this.vertexIndices = new HashMap<>();

        if (vertices != null) {
            for (String v : vertices) {
                if (v != null && !vertexIndices.containsKey(v)) {
                    vertexIndices.put(v, vertexList.size());
                    vertexList.add(v);
                }
            }
        }
        int n = vertexList.size();
        this.matrix = new boolean[n][n];
    }

    public boolean addEdge(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        Integer i = vertexIndices.get(first);
        Integer j = vertexIndices.get(second);
        if (i == null || j == null || matrix[i][j]) {
            return false;
        }

        matrix[i][j] = true;
        matrix[j][i] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        Integer i = vertexIndices.get(first);
        Integer j = vertexIndices.get(second);
        if (i == null || j == null || !matrix[i][j]) {
            return false;
        }

        matrix[i][j] = false;
        matrix[j][i] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        Integer i = vertexIndices.get(first);
        Integer j = vertexIndices.get(second);
        if (i == null || j == null) {
            return false;
        }
        return matrix[i][j];
    }

    public int degree(String vertex) {
        if (vertex == null) {
            return 0;
        }
        Integer i = vertexIndices.get(vertex);
        if (i == null) {
            return 0;
        }

        int deg = 0;
        for (int j = 0; j < vertexList.size(); j++) {
            if (matrix[i][j]) {
                deg++;
            }
        }
        return deg;
    }

    public List<String> neighbors(String vertex) {
        if (vertex == null) {
            return Collections.emptyList();
        }
        Integer i = vertexIndices.get(vertex);
        if (i == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (int j = 0; j < vertexList.size(); j++) {
            if (matrix[i][j]) {
                result.add(vertexList.get(j));
            }
        }
        return result;
    }
}
