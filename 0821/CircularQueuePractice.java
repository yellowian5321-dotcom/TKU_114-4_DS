
import java.util.Arrays;

public class CircularQueuePractice {

    public static class CircularQueue<T> {

        private Object[] queue;
        private int front = 0;
        private int rear = 0;
        private int size = 0;
        private int capacity;

        public CircularQueue(int capacity) {
            this.capacity = capacity;
            this.queue = new Object[capacity];
        }

        public boolean enqueue(T item) {
            if (size == capacity) {
                System.out.println("隊列已滿，無法加入: " + item);
                return false;
            }
            queue[rear] = item;
            rear = (rear + 1) % capacity;
            size++;
            return true;
        }

        @SuppressWarnings("unchecked")
        public T dequeue() {
            if (size == 0) {
                System.out.println("隊列已空，無法出隊");
                return null;
            }
            T item = (T) queue[front];
            queue[front] = null;
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        public void printState() {
            System.out.println("內部陣列: " + Arrays.toString(queue)
                    + ", front: " + front + ", rear: " + rear + ", size: " + size);
        }

        public void drainAndPrintFIFO() {
            System.out.print("FIFO 順序輸出: ");
            while (size > 0) {
                System.out.print(dequeue() + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularQueue<String> cq = new CircularQueue<>(4);

        System.out.println("--- 依序執行指定操作 ---");
        cq.enqueue("A");
        cq.enqueue("B");
        cq.enqueue("C");
        cq.dequeue();
        cq.dequeue();
        cq.enqueue("D");
        cq.enqueue("E");
        cq.enqueue("F");
        cq.dequeue();
        cq.enqueue("G");

        System.out.println("\n--- 目前狀態追蹤 ---");
        cq.printState();

        System.out.println("\n--- 取出所有元素 ---");
        cq.drainAndPrintFIFO();
        cq.printState();
    }
}
