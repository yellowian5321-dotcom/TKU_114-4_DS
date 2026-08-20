
abstract class Transport {

    protected String routeName;

    public Transport(String routeName) {
        this.routeName = routeName;
    }

    public abstract double calculateFare(int distance);

    public String getRouteName() {
        return routeName;
    }
}

class Bus extends Transport {

    private static final double BASE_FARE = 15.0;

    public Bus(String routeName) {
        super(routeName);
    }

    @Override
    public double calculateFare(int distance) {
        return BASE_FARE + (distance > 10 ? (distance - 10) * 1.5 : 0);
    }
}

class Taxi extends Transport {

    private static final double STARTING_FARE = 85.0;

    public Taxi(String routeName) {
        super(routeName);
    }

    @Override
    public double calculateFare(int distance) {
        return STARTING_FARE + (distance * 25.0);
    }
}

public class TransportFareSystem {

    public static void main(String[] args) {
        Transport[] transports = new Transport[]{
            new Bus("市區公車 307"),
            new Bus("快速公車 910"),
            new Taxi("台灣大車隊 A1"),
            new Taxi("多元計程車 T2")
        };

        int distance = 15;
        for (Transport t : transports) {
            System.out.printf("路線: %-12s | 距離: %2d km | 票價: $%.2f%n",
                    t.getRouteName(), distance, t.calculateFare(distance));
        }
    }
}
