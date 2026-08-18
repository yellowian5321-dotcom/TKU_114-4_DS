
class Equipment {

    private String id;
    private String name;
    private int availableCount;

    public Equipment(String id, String name, int availableCount) {
        this.id = (id == null || id.trim().isEmpty()) ? "Unknown" : id;
        this.name = (name == null || name.trim().isEmpty()) ? "Unknown" : name;
        this.availableCount = (availableCount < 0) ? 0 : availableCount;
    }

    public boolean borrowOne() {
        if (this.availableCount > 0) {
            this.availableCount--;
            return true;
        }
        return false;
    }

    public void returnItems(int quantity) {
        if (quantity > 0) {
            this.availableCount += quantity;
        }
    }

    @Override
    public String toString() {
        return "設備編號: " + id + ", 名稱: " + name + ", 可借數量: " + availableCount;
    }
}

public class EquipmentInventory {

    public static void main(String[] args) {
        Equipment eq1 = new Equipment("EQ001", "投影機", 1);
        Equipment eq2 = new Equipment("", "", -5);

        System.out.println("=== 初始狀態 ===");
        System.out.println(eq1);
        System.out.println(eq2);

        System.out.println("\n=== 借用測試 ===");
        System.out.println("EQ001 第一次借用: " + (eq1.borrowOne() ? "成功" : "失敗"));
        System.out.println("EQ001 第二次借用: " + (eq1.borrowOne() ? "成功" : "失敗"));
        System.out.println("當前 EQ001: " + eq1);

        System.out.println("\n=== 歸還測試 ===");
        eq1.returnItems(2);
        eq1.returnItems(-1); // 負數不應加入
        System.out.println("歸還 2 個後 EQ001: " + eq1);
    }
}
