
class Transaction {

    private int sequence;
    private String type; // "DEPOSIT", "PAYMENT", "TRANSFER_IN", "TRANSFER_OUT"
    private double amount;
    private String note;

    public Transaction(int sequence, String type, double amount, String note) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.note = note;
    }

    public int getSequence() {
        return sequence;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("[序號: #%02d | 類型: %-12s | 金額: %7.1f | 備註: %s]",
                sequence, type, amount, note);
    }
}

class Wallet {

    private String walletId;
    private String owner;
    private double balance;
    private Transaction[] history;
    private int txCount;
    private static int globalSequence = 1;

    public Wallet(String walletId, String owner, double initialBalance, int maxHistory) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = Math.max(initialBalance, 0.0);
        this.history = new Transaction[Math.max(maxHistory, 1)];
        this.txCount = 0;
    }

    public boolean hasSpaceForTx() {
        return txCount < history.length;
    }

    public boolean deposit(double amount, String note) {
        if (amount <= 0 || !hasSpaceForTx()) {
            return false;
        }
        balance += amount;
        history[txCount++] = new Transaction(globalSequence++, "DEPOSIT", amount, note);
        return true;
    }

    public boolean pay(double amount, String note) {
        if (amount <= 0 || balance < amount || !hasSpaceForTx()) {
            return false;
        }
        balance -= amount;
        history[txCount++] = new Transaction(globalSequence++, "PAYMENT", amount, note);
        return true;
    }

    public boolean transferTo(Wallet target, double amount) {
        if (target == null || target == this) {
            return false;
        }
        if (amount <= 0 || this.balance < amount) {
            return false;
        }
        // 關鍵防護：任一方陣列已滿均不可轉帳，餘額不得改變
        if (!this.hasSpaceForTx() || !target.hasSpaceForTx()) {
            System.out.println("【轉帳失敗】雙方之一交易記錄陣列已滿，拒絕交易以保障資料一致性。");
            return false;
        }

        this.balance -= amount;
        target.balance += amount;

        int currentSeq = globalSequence++;
        this.history[this.txCount++] = new Transaction(currentSeq, "TRANSFER_OUT", amount, "轉出給 " + target.owner);
        target.history[target.txCount++] = new Transaction(currentSeq, "TRANSFER_IN", amount, "接收自 " + this.owner);
        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < txCount; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    public double totalByType(String type) {
        if (type == null) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < txCount; i++) {
            if (type.equalsIgnoreCase(history[i].getType())) {
                sum += history[i].getAmount();
            }
        }
        return sum;
    }

    public void printStatement() {
        System.out.println("==================================================");
        System.out.printf("帳單報表 - 錢包: %s | 擁有者: %s | 餘額: %.2f\n", walletId, owner, balance);
        System.out.println("---------------- 歷史交易清單 --------------------");
        if (txCount == 0) {
            System.out.println(" (無交易紀錄)");
        } else {
            for (int i = 0; i < txCount; i++) {
                System.out.println(history[i]);
            }
        }
        System.out.println("==================================================");
    }
}

public class WalletHistoryManager {

    public static void main(String[] args) {
        Wallet w1 = new Wallet("W01", "陳雨紅", 3000.0, 5);
        Wallet w2 = new Wallet("W02", "王小明", 1000.0, 5);

        System.out.println("=== 執行儲值與付款 ===");
        w1.deposit(1000.0, "ATM 現金儲值");
        w1.pay(500.0, "購買生活用品");

        System.out.println("\n=== 執行跨錢包轉帳 ===");
        w1.transferTo(w2, 1200.0);

        System.out.println("\n=== 輸出雙方完整對帳單 ===");
        w1.printStatement();
        w2.printStatement();

        System.out.println("\n=== 依交易類型統計 (w1) ===");
        System.out.printf("w1 總儲值金額: %.2f 元\n", w1.totalByType("DEPOSIT"));
        System.out.printf("w1 總付款金額: %.2f 元\n", w1.totalByType("PAYMENT"));
        System.out.printf("w1 總轉出金額: %.2f 元\n", w1.totalByType("TRANSFER_OUT"));

        System.out.println("\n=== 依序號查詢交易紀錄 ===");
        int searchSeq = 3;
        Transaction found = w1.findTransaction(searchSeq);
        System.out.println("查詢序號 #" + searchSeq + " 交易: " + (found != null ? found : "查無紀錄"));

        int invalidSeq = 99;
        Transaction notFound = w1.findTransaction(invalidSeq);
        System.out.println("查詢序號 #" + invalidSeq + " 交易: " + (notFound != null ? notFound : "查無紀錄 (回傳 null)"));
    }
}
