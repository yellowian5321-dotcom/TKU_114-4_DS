
import java.util.ArrayList;
import java.util.List;

class Repository<T> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        System.out.println("Repository 資料清單 (總數: " + items.size() + "):");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(" [" + i + "] " + items.get(i));
        }
    }
}

class Product {

    private final String name;
    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

public class GenericRepositorySystem {

    public static void main(String[] args) {
        Repository<String> strRepo = new Repository<>();
        strRepo.add("Java");
        strRepo.add("Spring Boot");
        strRepo.printAll();

        Repository<Product> prodRepo = new Repository<>();
        prodRepo.add(new Product("Switch", 9780));
        prodRepo.add(new Product("PS5", 17580));
        prodRepo.printAll();
    }
}
