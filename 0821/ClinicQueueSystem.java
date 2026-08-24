
import java.util.*;

public class ClinicQueueSystem {

    static class Patient {

        String medicalId;
        String name;

        public Patient(String medicalId, String name) {
            this.medicalId = medicalId;
            this.name = name;
        }

        @Override
        public String toString() {
            return "[" + medicalId + " - " + name + "]";
        }
    }

    private final List<Patient> waitingQueue = new LinkedList<>();
    private final List<Patient> completedList = new ArrayList<>();

    public void register(String medicalId, String name) {
        waitingQueue.add(new Patient(medicalId, name));
        System.out.println("掛號成功: " + medicalId + " - " + name);
    }

    public void cancel(String medicalId) {
        Iterator<Patient> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Patient p = iterator.next();
            if (p.medicalId.equals(medicalId)) {
                iterator.remove();
                System.out.println("取消掛號成功: " + p);
                return;
            }
        }
        System.out.println("取消失敗：查無病歷號 " + medicalId);
    }

    public void callNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("目前無等候病人。");
            return;
        }
        Patient p = waitingQueue.remove(0); // FIFO 叫號
        completedList.add(p);
        System.out.println(">> 叫號看診: " + p);
    }

    public void peekNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("下一位: 無等候人員");
        } else {
            System.out.println("下一位候診者: " + waitingQueue.get(0));
        }
    }

    public void printCompleted() {
        System.out.println("當日完診名單 (" + completedList.size() + " 位): " + completedList);
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();
        clinic.register("P001", "陳先生");
        clinic.register("P002", "林小姐");
        clinic.register("P003", "黃先生");
        clinic.peekNext();
        clinic.cancel("P002");
        clinic.cancel("P999"); // 取消不存在測試
        clinic.callNext();
        clinic.callNext();
        clinic.callNext(); // 空隊列叫號
        clinic.printCompleted();
    }
}
