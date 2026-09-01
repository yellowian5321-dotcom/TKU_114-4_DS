
import java.util.*;

public class CampusMatrixGraph {

    private final int[][] matrix;
    private final int numVertices;
    private int edgeCount;
    private final Map<Integer, String> vertexNames;

    public CampusMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.matrix = new int[vertices][vertices];
        this.edgeCount = 0;
        this.vertexNames = new HashMap<>();
    }

    public void setVertexName(int index, String name) {
        vertexNames.put(index, name);
    }

    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (u == v) {
            return; // 忽略自環

                }if (matrix[u][v] == 0) {
            matrix[u][v] = 1;
            matrix[v][u] = 1;
            edgeCount++;
        }
    }

    public void removeEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (matrix[u][v] == 1) {
            matrix[u][v] = 0;
            matrix[v][u] = 0;
            edgeCount--;
        }
    }

    public int getDegree(int u) {
        validateVertex(u);
        int degree = 0;
        for (int i = 0; i < numVertices; i++) {
            degree += matrix[u][i];
        }
        return degree;
    }

    public List<Integer> getNeighbors(int u) {
        validateVertex(u);
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (matrix[u][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= numVertices) {
            throw new IndexOutOfBoundsException("節點編號超出範圍: " + v);
        }
    }

    public void printGraph() {
        System.out.println("--- 校園地圖鄰接矩陣 (邊數: " + edgeCount + ") ---");
        for (int i = 0; i < numVertices; i++) {
            String name = vertexNames.getOrDefault(i, "V" + i);
            System.out.printf("%-10s (度: %d) -> 鄰居: %s%n", name, getDegree(i), getNeighbors(i));
        }
    }

    public static void main(String[] args) {
        CampusMatrixGraph campus = new CampusMatrixGraph(4);
        campus.setVertexName(0, "圖書館");
        campus.setVertexName(1, "資工館");
        campus.setVertexName(2, "活動中心");
        campus.setVertexName(3, "運動場");

        campus.addEdge(0, 1);
        campus.addEdge(0, 2);
        campus.addEdge(1, 3);
        campus.addEdge(0, 1); // 測試重複加邊

        campus.printGraph();
    }
}
