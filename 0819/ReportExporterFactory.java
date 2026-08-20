
interface ReportExporter {

    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {

    @Override
    public void export(String title, int[] values) {
        System.out.print("[CSV] " + title + ": ");
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? "," : ""));
            }
        }
        System.out.println();
    }
}

class JsonExporter implements ReportExporter {

    @Override
    public void export(String title, int[] values) {
        System.out.print("[JSON] {\"title\":\"" + title + "\", \"data\":[");
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? ", " : ""));
            }
        }
        System.out.println("]}");
    }
}

class TextExporter implements ReportExporter {

    @Override
    public void export(String title, int[] values) {
        System.out.print("[TEXT] " + title + " -> ");
        if (values == null || values.length == 0) {
            System.out.println("(無資料)");
            return;
        }
        for (int v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}

public class ReportExporterFactory {

    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }
        return switch (format.trim().toUpperCase()) {
            case "CSV" ->
                new CsvExporter();
            case "JSON" ->
                new JsonExporter();
            default ->
                new TextExporter();
        };
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        exporter.export(title, values);
    }

    public static void main(String[] args) {
        int[] data = {120, 350, 480, 920};

        ReportExporter exp1 = createExporter("CSV");
        ReportExporter exp2 = createExporter("JSON");
        ReportExporter exp3 = createExporter("XML"); // 不支援格式，預設 TextExporter
        ReportExporter exp4 = createExporter(null);

        exportReport(exp1, "第一季業績", data);
        exportReport(exp2, "第一季業績", data);
        exportReport(exp3, "第一季業績", data);
        exportReport(exp4, "第一季業績", null); // null 測試
    }
}
