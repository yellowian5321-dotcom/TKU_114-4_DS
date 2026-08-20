
interface DeliveryMethod {

    double calculateCost(double weight);

    String getInvoiceDescription();
}

class HomeDelivery implements DeliveryMethod {

    @Override
    public double calculateCost(double weight) {
        return 100 + (weight * 10);
    }

    @Override
    public String getInvoiceDescription() {
        return "宅配到府服務 (含基本搬運費)";
    }
}

class ConvenienceStorePickup implements DeliveryMethod {

    @Override
    public double calculateCost(double weight) {
        return 60.0;
    }

    @Override
    public String getInvoiceDescription() {
        return "超商純取貨 (7-11/全家)";
    }
}

class InStorePickup implements DeliveryMethod {

    @Override
    public double calculateCost(double weight) {
        return 0.0;
    }

    @Override
    public String getInvoiceDescription() {
        return "門市現場自取 (免運費)";
    }
}

class OrderService {

    private DeliveryMethod deliveryMethod;

    public OrderService(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void generateInvoice(String orderId, double weight) {
        double fee = deliveryMethod.calculateCost(weight);
        String desc = deliveryMethod.getInvoiceDescription();
        System.out.printf("訂單 [%s] | 運費: $%.2f | 說明: %s%n", orderId, fee, desc);
    }
}

public class DeliveryStrategySystem {

    public static void main(String[] args) {
        OrderService service = new OrderService(new HomeDelivery());
        service.generateInvoice("ORD-001", 5.5);

        service.setDeliveryMethod(new ConvenienceStorePickup());
        service.generateInvoice("ORD-002", 2.0);

        service.setDeliveryMethod(new InStorePickup());
        service.generateInvoice("ORD-003", 1.2);
    }
}
