
class Result<T> {

    private final boolean success;
    private final String message;
    private final T data;

    private Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{success=" + success + ", message='" + message + "', data=" + data + "}";
    }
}

public class GenericResultDemo {

    public static void main(String[] args) {
        Result<String> strSuccess = Result.ok("Hello World", "載入成功");
        Result<String> strFail = Result.fail("找不到使用者");

        Result<Integer> intSuccess = Result.ok(100, "計算完成");
        Result<Integer> intFail = Result.fail("除數為零");

        System.out.println(strSuccess);
        System.out.println(strFail);
        System.out.println(intSuccess);
        System.out.println(intFail);

        if (strSuccess.isSuccess()) {
            String val = strSuccess.getData();
            System.out.println("提取字串: " + val);
        }
    }
}
