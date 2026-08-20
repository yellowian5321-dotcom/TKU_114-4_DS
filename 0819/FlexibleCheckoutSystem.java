
interface PricingPolicy {

    double calculateFinalPrice(double originalPrice);

    String getPolicyName();
}

class RegularPricing implements PricingPolicy {

    @Override
    public double calculateFinalPrice(double originalPrice) {
        return Math.max(0, originalPrice);
    }

    @Override
    public String getPolicyName() {
        return "原價";
    }
}

class VipDiscountPricing implements PricingPolicy {

    @Override
    public double calculateFinalPrice(double originalPrice) {
        return Math.max(0, originalPrice) * 0.85;
    }

    @Override
    public String getPolicyName() {
        return "VIP 85 折";
    }
}

class ThresholdDiscountPricing implements PricingPolicy {

    @Override
    public double calculateFinalPrice(double originalPrice) {
        double price = Math.max(0, originalPrice);
        return price >= 2000 ? price - 300 : price;
    }

    @Override
    public String getPolicyName() {
        return "滿 2000 折 300";
    }
}

interface NotificationChannel {

    boolean sendNotification(String orderId, double finalPrice);
}

class EmailNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String orderId, double finalPrice) {
        System.out.println("[Email 通知] 訂單 " + orderId + " 結帳完成，金額: $" + finalPrice);
        return true;
    }
}

class SmsNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String orderId, double finalPrice) {
        System.out.println("[SMS 通知] 訂單 " + orderId + " 結帳成功，金額: $" + finalPrice);
        return true;
    }
}

class ConsoleNotification implements NotificationChannel {

    @Override
    public boolean sendNotification(String orderId, double finalPrice) {
        System.out.println("[Console 日誌] 訂單 " + orderId + " 交易成功，金額: $" + finalPrice);
        return true;
    }
}

class CheckoutResult {

    private String orderId;
    private double originalPrice;
    private double finalPrice;
    private boolean notificationStatus;

    public CheckoutResult(String orderId, double originalPrice, double finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return String.format("結帳結果 [訂單: %s | 原價: $%.2f | 實付: $%.2f | 通知狀態: %s]",
                orderId, originalPrice, finalPrice, notificationStatus ? "成功" : "失敗");
    }
}

public class FlexibleCheckoutSystem {

    public static CheckoutResult checkout(String orderId, double originalPrice, PricingPolicy pricing, NotificationChannel channel) {
        double finalPrice = pricing.calculateFinalPrice(originalPrice);
        boolean notified = channel.sendNotification(orderId, finalPrice);
        return new CheckoutResult(orderId, originalPrice, finalPrice, notified);
    }

    public static void main(String[] args) {
        PricingPolicy regular = new RegularPricing();
        PricingPolicy vip = new VipDiscountPricing();
        PricingPolicy threshold = new ThresholdDiscountPricing();

        NotificationChannel email = new EmailNotification();
        NotificationChannel sms = new SmsNotification();
        NotificationChannel console = new ConsoleNotification();

        // 測試 6 種 定價/通路 組合
        System.out.println(checkout("ORD-101", 1500, regular, email));
        System.out.println(checkout("ORD-102", 1500, vip, sms));
        System.out.println(checkout("ORD-103", 2500, threshold, console));
        System.out.println(checkout("ORD-104", 3000, vip, email));
        System.out.println(checkout("ORD-105", 1800, threshold, sms));
        System.out.println(checkout("ORD-106", 500, regular, console));
    }
}
