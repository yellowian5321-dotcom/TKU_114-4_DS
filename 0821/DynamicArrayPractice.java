
import java.util.Arrays;

public class DynamicArrayPractice {

    public static class DynamicArray<T> {

        private Object[] data;
        private int size;

        public DynamicArray() {
            this(2);
        }

        public DynamicArray(int initCapacity) {
            this.data = new Object[initCapacity];
            this.size = 0;
        }

        public void add(T value) {
            add(size, value);
        }

        public void add(int index, T value) {
            if (index < 0 || index > size) {
                System.out.println("新增失敗：索引越界 " + index);
                return;
            }
            if (size == data.length) {
                resize(data.length * 2);
            }
            for (int i = size; i > index; i--) {
                data[i] = data[i - 1];
            }
            data[index] = value;
            size++;
        }

        @SuppressWarnings("unchecked")
        public T get(int index) {
            if (index < 0 || index >= size) {
                System.out.println("獲取失敗：索引越界 " + index);
                return null;
            }
            return (T) data[index];
        }

        @SuppressWarnings("unchecked")
        public T set(int index, T value) {
            if (index < 0 || index >= size) {
                System.out.println("設置失敗：索引越界 " + index);
                return null;
            }
            T old = (T) data[index];
            data[index] = value;
            return old;
        }

        @SuppressWarnings("unchecked")
        public T remove(int index) {
            if (index < 0 || index >= size) {
                System.out.println("刪除失敗：索引越界 " + index);
                return null;
            }
            T removed = (T) data[index];
            for (int i = index; i < size - 1; i++) {
                data[i] = data[i + 1];
            }
            data[--size] = null; // 設為 null 防止記憶體洩漏
            return removed;
        }

        public int size() {
            return size;
        }

        public int capacity() {
            return data.length;
        }

        private void resize(int newCapacity) {
            data = Arrays.copyOf(data, newCapacity);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                sb.append(data[i]).append(i == size - 1 ? "" : ", ");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 測試 String 型別與邊界情況 ---");
        DynamicArray<String> strArr = new DynamicArray<>();
        strArr.remove(0); // 空結構刪除測試
        strArr.add("Hello");
        strArr.add("World");
        strArr.add("Java"); // 觸發擴容
        System.out.println("元素: " + strArr + ", Size: " + strArr.size() + ", Cap: " + strArr.capacity());
        strArr.remove(-1);  // 索引 -1 測試
        strArr.remove(strArr.size()); // 索引等於 size 測試
        strArr.remove(1);
        System.out.println("移除後: " + strArr);

        System.out.println("\n--- 測試 Integer 型別 ---");
        DynamicArray<Integer> intArr = new DynamicArray<>();
        intArr.add(10);
        intArr.add(0, 5);
        intArr.set(1, 20);
        System.out.println("元素: " + intArr + ", Get(0): " + intArr.get(0));
    }
}
