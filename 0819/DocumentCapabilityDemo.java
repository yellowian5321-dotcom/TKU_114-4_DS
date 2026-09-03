
interface Exportable {

    void exportFile(String path);
}

interface Compressible {

    void compress(String zipPath);
}

class BackupDocument implements Exportable, Compressible {

    private String docName;

    public BackupDocument(String docName) {
        this.docName = docName;
    }

    @Override
    public void exportFile(String path) {
        System.out.println("匯出文件 [" + docName + "] 至路徑: " + path);
    }

    @Override
    public void compress(String zipPath) {
        System.out.println("壓縮文件 [" + docName + "] 至壓縮檔: " + zipPath);
    }
}

public class DocumentCapabilityDemo {

    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("2026_年度財報.docx");

        // 兩個介面參考指向同一個記憶體物件
        Exportable expRef = doc;
        Compressible compRef = doc;

        System.out.println("expRef 與 compRef 是否指向同一物件: " + (expRef == compRef));

        // 透過介面限定呼叫的方法範圍（可見性不同）
        expRef.exportFile("/exports/report.pdf");
        compRef.compress("/archives/report.zip");
    }
}
