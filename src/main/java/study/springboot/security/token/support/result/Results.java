package study.springboot.security.token.support.result;

import com.google.common.collect.Maps;

public final class Results {

    protected static final String SUCC_CODE = "0000";

    protected static final String SUCC_DESC = "成功";

    private Results() {
    }

    public static Result success() {
        return success(null);
    }

    public static <T> Result success(T data) {
        if (data == null) {
            data = (T) Maps.newHashMap();
        }
        Result rst = new Result(data);
        return rst;
    }

    public static Result fail(String code, String desc) {
        return new Result(code, desc);
    }

}
