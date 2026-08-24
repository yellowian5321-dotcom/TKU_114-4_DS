
public class GenericArrayStackDemo {

    public static class ArrayStack<T> {

        private Object[] elements;
        private int top = -1;
        private int capacity;

        public ArrayStack(int capacity) {
            this.capacity = capacity;
            this.elements = new Object[capacity];
        }

        public void push(T value) {
            if (isFull()) {
                System.out.println("棧已滿，無法推入: " + value);
                return;
            }
            elements[++top] = value;
        }

        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                System.out.println("棧已空，無法彈出");
                return null;
            }
            T val = (T) elements[top];
            elements[top--] = null; // 釋放記憶體
            return val;
        }

        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                return null;
            }
            return (T) elements[top];
        }

        public int size() {
            return top + 1;
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == capacity - 1;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 測試 ArrayStack<String> ---");
        ArrayStack<String> strStack = new ArrayStack<>(2);
        strStack.push("A");
        strStack.push("B");
        strStack.push("C"); // 滿棧測試
        System.out.println("Peek: " + strStack.peek());
        System.out.println("Pop: " + strStack.pop());
        System.out.println("Pop: " + strStack.pop());
        System.out.println("Pop: " + strStack.pop()); // 空棧測試

        System.out.println("\n--- 測試 ArrayStack<Integer> ---");
        ArrayStack<Integer> intStack = new ArrayStack<>(3);
        intStack.push(100);
        intStack.push(200);
        System.out.println("Size: " + intStack.size());
        System.out.println("Pop: " + intStack.pop());
    }
}
