
abstract class Device {

    protected String name;

    public Device(String name) {
        this.name = name;
    }

    public abstract void runDiagnostic();
}

class Laptop extends Device {

    public Laptop(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[筆電 " + name + "] 記憶體與電池狀態檢測正常。");
    }
}

class Printer extends Device {

    public Printer(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[印表機 " + name + "] 墨水量及進紙滾輪檢測正常。");
    }

    public void cleanPrintHead() {
        System.out.println("[印表機 " + name + "] 正在執行噴頭清潔程序...");
    }
}

class Router extends Device {

    public Router(String name) {
        super(name);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[路由器 " + name + "] 封包傳輸率與連線埠診斷正常。");
    }
}

public class DeviceInspectionSystem {

    public static void main(String[] args) {
        Device[] devices = new Device[]{
            new Laptop("ThinkPad X1"),
            new Printer("Epson L3210"),
            new Router("ASUS RT-AX86U"),
            new Printer("HP LaserJet Pro")
        };

        for (Device dev : devices) {
            dev.runDiagnostic();
            // Java 16+ Pattern Matching for instanceof
            if (dev instanceof Printer printer) {
                printer.cleanPrintHead();
            }
            System.out.println("----------------------------------------");
        }
    }
}
