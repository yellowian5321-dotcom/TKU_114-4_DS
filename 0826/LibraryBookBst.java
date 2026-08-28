
import java.util.ArrayList;
import java.util.List;

public class LibraryBookBst {

    static class Book {

        String isbn; // Key
        String title;
        String author;
        boolean available;

        Book(String isbn, String title, String author, boolean available) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.available = available;
        }

        @Override
        public String toString() {
            return String.format("Book[ISBN='%s', Title='%s', Author='%s', Available=%s]", isbn, title, author, available);
        }
    }

    static class Node {

        Book book;
        Node left, right;

        Node(Book book) {
            this.book = book;
        }
    }

    private Node root;

    public boolean insert(String isbn, String title, String author) {
        if (find(isbn) != null) {
            System.out.println("新增失敗: ISBN " + isbn + " 已存在！");
            return false;
        }
        root = insertRec(root, new Book(isbn, title, author, true));
        return true;
    }

    private Node insertRec(Node cur, Book book) {
        if (cur == null) {
            return new Node(book);
        }
        int cmp = book.isbn.compareTo(cur.book.isbn);
        if (cmp < 0) {
            cur.left = insertRec(cur.left, book); 
        }else {
            cur.right = insertRec(cur.right, book);
        }
        return cur;
    }

    public Book find(String isbn) {
        Node cur = root;
        while (cur != null) {
            int cmp = isbn.compareTo(cur.book.isbn);
            if (cmp == 0) {
                return cur.book;
            }
            cur = (cmp < 0) ? cur.left : cur.right;
        }
        return null;
    }

    public boolean borrowBook(String isbn) {
        Book b = find(isbn);
        if (b == null || !b.available) {
            System.out.println("借閱失敗: 書籍不存在或已借出！");
            return false;
        }
        b.available = false;
        return true;
    }

    public boolean returnBook(String isbn) {
        Book b = find(isbn);
        if (b == null || b.available) {
            System.out.println("歸還失敗: 書籍不存在或在庫中！");
            return false;
        }
        b.available = true;
        return true;
    }

    public boolean delete(String isbn) {
        Book b = find(isbn);
        if (b == null) {
            System.out.println("刪除失敗: 查無此 ISBN！");
            return false;
        }
        if (!b.available) {
            System.out.println("刪除失敗: 書籍「" + b.title + "」借出中，不可刪除！");
            return false;
        }
        root = deleteRec(root, isbn);
        return true;
    }

    private Node deleteRec(Node cur, String isbn) {
        if (cur == null) {
            return null;
        }
        int cmp = isbn.compareTo(cur.book.isbn);
        if (cmp < 0) {
            cur.left = deleteRec(cur.left, isbn); 
        }else if (cmp > 0) {
            cur.right = deleteRec(cur.right, isbn); 
        }else {
            if (cur.left == null) {
                return cur.right;
            }
            if (cur.right == null) {
                return cur.left;
            }
            Node min = findMin(cur.right);
            cur.book = min.book;
            cur.right = deleteRec(cur.right, min.book.isbn);
        }
        return cur;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Book> findByIsbnRange(String lowIsbn, String highIsbn) {
        List<Book> list = new ArrayList<>();
        rangeRec(root, lowIsbn, highIsbn, list);
        return list;
    }

    private void rangeRec(Node cur, String low, String high, List<Book> list) {
        if (cur == null) {
            return;
        }
        if (cur.book.isbn.compareTo(low) > 0) {
            rangeRec(cur.left, low, high, list);
        }
        if (cur.book.isbn.compareTo(low) >= 0 && cur.book.isbn.compareTo(high) <= 0) {
            list.add(cur.book);
        }
        if (cur.book.isbn.compareTo(high) < 0) {
            rangeRec(cur.right, low, high, list);
        }
    }

    public void printInorderReport() {
        System.out.println("--- 圖書館藏 ISBN 排序報表 ---");
        inorder(root);
        System.out.println("-----------------------------");
    }

    private void inorder(Node n) {
        if (n == null) {
            return;
        }
        inorder(n.left);
        System.out.println(n.book);
        inorder(n.right);
    }

    public static void main(String[] args) {
        LibraryBookBst lib = new LibraryBookBst();
        lib.insert("978-0134685991", "Effective Java", "Joshua Bloch");
        lib.insert("978-0321356680", "Effective Java 2nd", "Joshua Bloch");
        lib.insert("978-0132350884", "Clean Code", "Robert C. Martin");

        lib.printInorderReport();
        lib.borrowBook("978-0132350884"); // 借出 Clean Code
        lib.delete("978-0132350884");     // 刪除失敗 (借出中)
        lib.returnBook("978-0132350884"); // 歸還
        lib.delete("978-0132350884");     // 刪除成功
        lib.printInorderReport();
    }
}
