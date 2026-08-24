
import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {

    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();
    private String currentText = "";

    public void write(String newText) {
        undoStack.push(currentText);
        currentText = newText;
        redoStack.clear(); // 新增操作時清空 redo
        printStatus("新增內容: \"" + currentText + "\"");
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            printStatus("無法 Undo（已無歷史記錄）");
            return;
        }
        redoStack.push(currentText);
        currentText = undoStack.pop();
        printStatus("執行 Undo");
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            printStatus("無法 Redo（無可重做的操作）");
            return;
        }
        undoStack.push(currentText);
        currentText = redoStack.pop();
        printStatus("執行 Redo");
    }

    private void printStatus(String action) {
        System.out.println("[" + action + "] 目前文字: \"" + currentText + "\", UndoStack: " + undoStack + ", RedoStack: " + redoStack);
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();
        editor.undo();                 // 空棧測試
        editor.redo();                 // 空棧測試
        editor.write("Hello");
        editor.write("Hello World");
        editor.write("Hello World!");
        editor.undo();
        editor.undo();
        editor.redo();
        editor.write("Hello Java");    // 寫入後 redo 應被清空
        editor.redo();                 // 驗證 redo 是否為空
    }
}
