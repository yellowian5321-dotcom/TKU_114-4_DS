
interface MessageSender {

    void send(String receiver, String message);
}

class EmailSender implements MessageSender {

    @Override
    public void send(String receiver, String message) {
        System.out.println("[Email 發送] 收件人: " + receiver + " | 內容: " + message);
    }
}

class SmsSender implements MessageSender {

    @Override
    public void send(String receiver, String message) {
        System.out.println("[SMS 簡訊] 號碼: " + receiver + " | 內容: " + message);
    }
}

class ConsoleSender implements MessageSender {

    @Override
    public void send(String receiver, String message) {
        System.out.println("[控制台] 接收端: " + receiver + " | 訊息: " + message);
    }
}

public class MessageSenderSystem {

    public static void notify(MessageSender sender, String receiver, String message) {
        if (receiver == null || receiver.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            System.out.println("[錯誤] 發送失敗: 接收者或訊息內容不得為空。");
            return;
        }
        sender.send(receiver.trim(), message.trim());
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        notify(email, "user@example.com", "系統登入驗證碼: 123456");
        notify(sms, "0912345678", "包裹已送達指定門市");
        notify(console, "Admin", "伺服器重啟完成");
        notify(email, "", "測試空訊息");
    }
}
