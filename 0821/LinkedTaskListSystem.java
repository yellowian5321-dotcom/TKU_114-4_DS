
public class LinkedTaskListSystem {

    static class Task {

        String id;
        String title;

        public Task(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString() {
            return "Task(" + id + ": " + title + ")";
        }
    }

    static class TaskNode {

        Task task;
        TaskNode next;

        public TaskNode(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    public static class TaskLinkedList {

        private TaskNode head;
        private int size = 0;

        public boolean addFirst(Task task) {
            if (findById(task.id) != null) {
                System.out.println("新增失敗：重複 ID -> " + task.id);
                return false;
            }
            TaskNode newNode = new TaskNode(task);
            newNode.next = head;
            head = newNode;
            size++;
            return true;
        }

        public boolean addLast(Task task) {
            if (findById(task.id) != null) {
                System.out.println("新增失敗：重複 ID -> " + task.id);
                return false;
            }
            TaskNode newNode = new TaskNode(task);
            if (head == null) {
                head = newNode;
            } else {
                TaskNode curr = head;
                while (curr.next != null) {
                    curr = curr.next;
                }
                curr.next = newNode;
            }
            size++;
            return true;
        }

        public Task findById(String id) {
            TaskNode curr = head;
            while (curr != null) {
                if (curr.task.id.equals(id)) {
                    return curr.task;
                }
                curr = curr.next;
            }
            return null;
        }

        public boolean insertAfter(String existingId, Task task) {
            if (findById(task.id) != null) {
                System.out.println("插入失敗：新任務重複 ID -> " + task.id);
                return false;
            }
            TaskNode curr = head;
            while (curr != null) {
                if (curr.task.id.equals(existingId)) {
                    TaskNode newNode = new TaskNode(task);
                    newNode.next = curr.next;
                    curr.next = newNode;
                    size++;
                    return true;
                }
                curr = curr.next;
            }
            System.out.println("插入失敗：查無 ID " + existingId);
            return false;
        }

        public boolean removeById(String id) {
            if (head == null) {
                return false;
            }
            if (head.task.id.equals(id)) {
                head = head.next;
                size--;
                return true;
            }
            TaskNode curr = head;
            while (curr.next != null) {
                if (curr.next.task.id.equals(id)) {
                    curr.next = curr.next.next;
                    size--;
                    return true;
                }
                curr = curr.next;
            }
            return false;
        }

        public int size() {
            return size;
        }

        public void printAll() {
            System.out.print("鏈結串列內容 (size " + size + "): ");
            TaskNode curr = head;
            while (curr != null) {
                System.out.print(curr.task + " -> ");
                curr = curr.next;
            }
            System.out.println("null");
        }
    }

    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("--- 1. 空 list 刪除與查詢測試 ---");
        System.out.println("空串列刪除: " + list.removeById("T1"));
        System.out.println("空串列查詢: " + list.findById("T1"));

        System.out.println("\n--- 2. 插入節點與重複檢查 ---");
        list.addLast(new Task("T1", "架構設計"));
        list.addLast(new Task("T2", "撰寫程式碼"));
        list.addLast(new Task("T3", "單元測試"));
        list.addFirst(new Task("T0", "需求分析"));
        list.insertAfter("T2", new Task("T2.5", "Code Review"));
        list.addLast(new Task("T1", "重複任務測試")); // 重複測試
        list.printAll();

        System.out.println("\n--- 3. 刪除 Head, Middle, Tail 與 找不到 ID ---");
        list.removeById("T0");   // 刪除 Head
        System.out.print("刪除 Head 後: ");
        list.printAll();

        list.removeById("T2.5"); // 刪除 Middle
        System.out.print("刪除 Middle 後: ");
        list.printAll();

        list.removeById("T3");   // 刪除 Tail
        System.out.print("刪除 Tail 後: ");
        list.printAll();

        System.out.println("刪除不存在 ID: " + list.removeById("T999"));
    }
}
