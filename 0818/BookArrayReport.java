
class Book {

    private String id;
    private String title;
    private double price;
    private int stock;

    public Book(String id, String title, double price, int stock) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public double getTotalValue() {
        return price * stock;
    }

    @Override
    public String toString() {
        return String.format("書號: %s | 書名: %-15s | 價格: %6.1f | 庫存: %2d", id, title, price, stock);
    }
}

public class BookArrayReport {

    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java 核心技術", 680.0, 5),
            new Book("B002", "Python 基礎教學", 450.0, 2),
            new Book("B003", "演算法圖解", 520.0, 1),
            new Book("B004", "系統設計指南", 890.0, 4)
        };

        System.out.println("=== 所有書籍清單 ===");
        double totalInventoryValue = 0;
        Book highestPriceBook = books[0];

        for (Book book : books) {
            System.out.println(book);
            totalInventoryValue += book.getTotalValue();
            if (book.getPrice() > highestPriceBook.getPrice()) {
                highestPriceBook = book;
            }
        }

        System.out.println("\n=== 統計資訊 ===");
        System.out.printf("庫存總價值: %.2f\n", totalInventoryValue);
        System.out.println("單價最高書籍: " + highestPriceBook.getTitle() + " (價格: " + highestPriceBook.getPrice() + ")");

        System.out.println("\n=== 庫存小於或等於 3 的書籍 ===");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}
