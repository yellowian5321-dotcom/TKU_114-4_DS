
class Account {

    private String accountNumber;
    private String ownerName;
    private int balance;

    public Account(String accountNumber, String ownerName, int initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = Math.max(initialBalance, 0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return String.format("帳號: %-6s | 戶名: %-4s | 餘額: %5d 元", accountNumber, ownerName, balance);
    }
}

class TransferService {

    public static boolean transfer(Account source, Account target, int amount) {
        // 1. 來源與目標不可為 null
        if (source == null || target == null) {
            System.out.println("【轉帳失敗】來源帳戶或目標帳戶不可為 null！");
            return false;
        }

        // 2. 來源與目標不可為同一個物件實例
        if (source == target) {
            System.out.println("【轉帳失敗】不可轉帳給自身相同帳戶！");
            return false;
        }

        // 3. 轉帳金額需大於 0
        if (amount <= 0) {
            System.out.println("【轉帳失敗】轉帳金額必須大於 0: " + amount);
            return false;
        }

        // 4. 來源帳戶餘額必須足夠
        if (source.getBalance() < amount) {
            System.out.printf("【轉帳失敗】來源帳戶餘額不足！當前餘額: %d，轉帳需求: %d\n",
                    source.getBalance(), amount);
            return false;
        }

        // 驗證全數通過，執行原子轉帳操作
        source.withdraw(amount);
        target.deposit(amount);
        System.out.printf("【轉帳成功】從 [%s] 轉帳 %d 元至 [%s]\n",
                source.getOwnerName(), amount, target.getOwnerName());
        return true;
    }
}

public class AccountTransferService {

    public static void main(String[] args) {
        Account accA = new Account("ACC01", "陳雨紅", 5000);
        Account accB = new Account("ACC02", "林小明", 2000);

        System.out.println("=== 初始帳戶狀態 ===");
        System.out.println(accA);
        System.out.println(accB);

        System.out.println("\n=== 1. 測試成功轉帳 (1500元) ===");
        TransferService.transfer(accA, accB, 1500);
        System.out.println(accA);
        System.out.println(accB);

        System.out.println("\n=== 2. 測試餘額不足轉帳 (10000元) ===");
        TransferService.transfer(accA, accB, 10000);
        System.out.println(accA);
        System.out.println(accB);

        System.out.println("\n=== 3. 測試同帳戶轉帳 ===");
        TransferService.transfer(accA, accA, 500);
        System.out.println(accA);

        System.out.println("\n=== 4. 測試目標帳戶為 null ===");
        TransferService.transfer(accA, null, 500);
        System.out.println(accA);

        System.out.println("\n=== 5. 測試轉帳負數或零金額 ===");
        TransferService.transfer(accA, accB, -200);
        System.out.println(accA);
        System.out.println(accB);
    }
}
