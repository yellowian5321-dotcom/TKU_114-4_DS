
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q04_NotificationRouter {

    public interface Channel {

        String name();

        boolean supports(String destination);

        String send(String destination, String message);
    }

    public static class EmailChannel implements Channel {

        @Override
        public String name() {
            return "EMAIL";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            int atIdx = destination.indexOf('@');
            return atIdx > 0 && atIdx < destination.length() - 1 && destination.indexOf('@', atIdx + 1) == -1;
        }

        @Override
        public String send(String destination, String message) {
            return "EMAIL | " + destination + " | " + message;
        }
    }

    public static class SmsChannel implements Channel {

        @Override
        public String name() {
            return "SMS";
        }

        @Override
        public boolean supports(String destination) {
            if (destination == null) {
                return false;
            }
            String digits = destination.replace("-", "");
            return digits.matches("\\d{10}");
        }

        @Override
        public String send(String destination, String message) {
            return "SMS | " + destination + " | " + message;
        }
    }

    public static List<String> route(List<Channel> channels, String destination, String message) {
        if (channels == null || destination == null || message == null) {
            return Collections.emptyList();
        }
        List<String> results = new ArrayList<>();
        for (Channel ch : channels) {
            if (ch != null && ch.supports(destination)) {
                results.add(ch.send(destination, message));
            }
        }
        return results;
    }
}
