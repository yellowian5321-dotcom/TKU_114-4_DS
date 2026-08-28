
public class RecursiveCallReport {

    public static int sum(int[] data, int index) {
        if (data == null || index >= data.length) {
            System.out.printf("[Base Case] index: %d, data is empty or out of bounds -> return 0%n", index);
            return 0;
        }

        int currentValue = data[index];
        System.out.printf("[Call] index: %d, currentValue: %d, calling sum(data, %d)%n", index, currentValue, index + 1);

        int recursiveResult = sum(data, index + 1);
        int returnValue = currentValue + recursiveResult;

        System.out.printf("[Return] index: %d, currentValue: %d, recursiveResult: %d, returnValue: %d%n",
                index, currentValue, recursiveResult, returnValue);
        return returnValue;
    }

    public static void main(String[] args) {
        System.out.println("=== 測試 1: 一般陣列 ===");
        int[] arr1 = {3, 7, 2, 8};
        System.out.println("總和結果: " + sum(arr1, 0) + "\n");

        System.out.println("=== 測試 2: 單一元素陣列 ===");
        int[] arr2 = {42};
        System.out.println("總和結果: " + sum(arr2, 0) + "\n");

        System.out.println("=== 測試 3: 空陣列 ===");
        int[] arr3 = {};
        System.out.println("總和結果: " + sum(arr3, 0) + "\n");
    }
}
