
import java.util.*;

public class MetroMatrixGraph {

    private final String[] stations;
    private final Map<String, Integer> stationIndexMap;
    private final int[][] matrix;
    private int edgeCount = 0;

    public MetroMatrixGraph(String[] stationList) {
        this.stations = stationList.clone();
        this.stationIndexMap = new HashMap<>();
        int n = stationList.length;
        for (int i = 0; i < n; i++) {
            stationIndexMap.put(stationList[i], i);
        }
        this.matrix = new int[n][n];
    }

    public void addTrack(String stationA, String stationB) {
        Integer u = stationIndexMap.get(stationA);
        Integer v = stationIndexMap.get(stationB);
        if (u == null || v == null) {
            throw new IllegalArgumentException("站點名稱不存在");
        }
        if (matrix[u][v] == 0) {
            matrix[u][v] = 1;
            matrix[v][u] = 1;
            edgeCount++;
        }
    }

    public List<String> getAdjacentStations(String station) {
        Integer u = stationIndexMap.get(station);
        if (u == null) {
            return Collections.emptyList();
        }
        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < stations.length; i++) {
            if (matrix[u][i] == 1) {
                neighbors.add(stations[i]);
            }
        }
        return neighbors;
    }

    public void printReport() {
        System.out.printf("===== 捷運路網矩陣報告 (總站點: %d, 軌道路段數: %d) =====%n", stations.length, edgeCount);
        for (int i = 0; i < stations.length; i++) {
            List<String> adj = getAdjacentStations(stations[i]);
            System.out.printf("站點: %-8s | 連接度: %d | 相鄰站點: %s%n", stations[i], adj.size(), adj);
        }

        System.out.println("\n【鄰接矩陣檢視】");
        System.out.print("        ");
        for (String s : stations) {
            System.out.printf("%-6s", s);
        }
        System.out.println();
        for (int i = 0; i < stations.length; i++) {
            System.out.printf("%-8s", stations[i]);
            for (int j = 0; j < stations.length; j++) {
                System.out.printf("%-6d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[] stations = {"淡水", "紅樹林", "竹圍", "關渡", "忠義"};
        MetroMatrixGraph metro = new MetroMatrixGraph(stations);
        metro.addTrack("淡水", "紅樹林");
        metro.addTrack("紅樹林", "竹圍");
        metro.addTrack("竹圍", "關渡");
        metro.addTrack("關渡", "忠義");

        metro.printReport();
    }
}
