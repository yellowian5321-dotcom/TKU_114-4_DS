
import java.util.Arrays;

final class InventorySnapshot {

    private final String warehouseId;
    private final int[] quantities;

    public InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId;
        if (quantities == null) {
            this.quantities = new int[0];
        } else {
            this.quantities = Arrays.copyOf(quantities, quantities.length);
        }
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    public int totalQuantity() {
        int sum = 0;
        for (int q : quantities) {
            sum += q;
        }
        return sum;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }
}

public class InventorySnapshotPractice {

    public static void main(String[] args) {
        int[] rawData = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH-A1", rawData);

        System.out.println("倉庫編號: " + snapshot.getWarehouseId());
        System.out.println("商品總數: " + snapshot.totalQuantity());
        System.out.println("缺貨品項數: " + snapshot.outOfStockCount());

        // 測試防禦性複製是否有效（修改外部陣列與 getter 陣列）
        rawData[0] = 999;
        snapshot.getQuantities()[1] = 999;

        System.out.println("\n=== 驗證不可變性（外部修改後） ===");
        System.out.println("內部商品總數仍為: " + snapshot.totalQuantity());
        System.out.println("內部缺貨品項數仍為: " + snapshot.outOfStockCount());

        // 邊界測試：傳入 null
        InventorySnapshot nullSnapshot = new InventorySnapshot("WH-NULL", null);
        System.out.println("\n=== null 邊界測試 ===");
        System.out.println("陣列長度: " + nullSnapshot.getQuantities().length);
        System.out.println("總數: " + nullSnapshot.totalQuantity());
    }
}
