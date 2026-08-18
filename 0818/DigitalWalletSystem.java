
class DigitalWallet {

    private String walletId;
    private String ownerName;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String ownerName, double initialBalance) {
        this.walletId = (walletId == null || walletId.trim().isEmpty()) ? "Unknown" : walletId;
        this.ownerName = (ownerName == null || ownerName.trim().isEmpty()) ? "Unknown" : ownerName;
        this.balance = Math.max(initialBalance, 0.0);
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("【儲值失敗】儲值金額必須大於 0: " + amount);
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("【付款失敗】扣款金額必須大於 0: " + amount);
            return false;
        }
        if (this.balance < amount) {
            System.out.printf("【付款失敗】餘額不足！當前餘額: %.2f，嘗試扣款: %.2f\n", this.balance, amount);
            return false;
        }
        this.balance -= amount;
        this.transactionCount++;
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) {
            System.out.println("【退款失敗】退款金額必須大於 0: " + amount);
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public String toString() {
        return String.format("錢包ID: %s | 擁有者: %s | 當前餘額: %.2f | 累計成功交易次數: %d",
                walletId, ownerName, balance, transactionCount);
    }
}

public class DigitalWalletSystem {

    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W1001", "陳雨紅", 1000.0);
        System.out.println("=== 初始狀態 ===");
        System.out.println(wallet);

        System.out.println("\n=== 1. 測試正常儲值 ===");
        wallet.deposit(500.0);
        System.out.println(wallet);

        System.out.println("\n=== 2. 測試正常付款 ===");
        wallet.pay(300.0);
        System.out.println(wallet);

        System.out.println("\n=== 3. 測試餘額不足付款 ===");
        wallet.pay(2000.0);
        System.out.println(wallet);

        System.out.println("\n=== 4. 測試不合法負數操作 ===");
        wallet.deposit(-100.0);
        wallet.pay(-50.0);
        System.out.println(wallet);

        System.out.println("\n=== 5. 測試退款 ===");
        wallet.refund(200.0);
        System.out.println(wallet);
    }
}
