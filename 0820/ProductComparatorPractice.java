
import java.util.*;

class StoreProduct implements Comparable<StoreProduct> {

    private final int id;
    private final String name;
    private final double price;
    private final int stock;

    public StoreProduct(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public int compareTo(StoreProduct other) {
        return Integer.compare(this.id, other.id); // 自然排序：id 升冪
    }

    @Override
    public String toString() {
        return String.format("[ID:%d, Name:%-8s, Price:%.1f, Stock:%d]", id, name, price, stock);
    }
}

public class ProductComparatorPractice {

    public static void main(String[] args) {
        List<StoreProduct> products = Arrays.asList(
                new StoreProduct(105, "Keyboard", 1500.0, 30),
                new StoreProduct(101, "Mouse", 800.0, 50),
                new StoreProduct(103, "Monitor", 4500.0, 10),
                new StoreProduct(102, "Cable", 800.0, 100),
                new StoreProduct(104, "Headset", 1500.0, 10)
        );

        // 規則一：價格升冪，同價時依名稱升冪
        Comparator<StoreProduct> priceThenName = Comparator
                .comparingDouble(StoreProduct::getPrice)
                .thenComparing(StoreProduct::getName);

        // 規則二：庫存降冪，同庫存時依 ID 升冪
        Comparator<StoreProduct> stockDescThenId = Comparator
                .comparingInt(StoreProduct::getStock).reversed()
                .thenComparingInt(StoreProduct::getId);

        List<StoreProduct> naturalSorted = new ArrayList<>(products);
        Collections.sort(naturalSorted);

        List<StoreProduct> priceSorted = new ArrayList<>(products);
        priceSorted.sort(priceThenName);

        List<StoreProduct> stockSorted = new ArrayList<>(products);
        stockSorted.sort(stockDescThenId);

        System.out.println("--- 原始列表 ---");
        products.forEach(System.out::println);

        System.out.println("\n--- 自然排序 (ID 升冪) ---");
        naturalSorted.forEach(System.out::println);

        System.out.println("\n--- 價格升冪 (同價依名稱) ---");
        priceSorted.forEach(System.out::println);

        System.out.println("\n--- 庫存降冪 (同庫存依 ID) ---");
        stockSorted.forEach(System.out::println);
    }
}
