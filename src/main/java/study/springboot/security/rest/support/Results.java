package study.springboot.security.rest.support;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Results {

    private static final String CODE_KEY = "code";

    private static final String DESC_KEY = "desc";

    private static final String DATA_KEY = "data";

    public static Map<String, Object> ok() {
        return ok(null);
    }

    public static Map<String, Object> ok(Map<String, Object> data) {
        return builtRst("0000", "成功", data);
    }

    public static Map<String, Object> error() {
        return error("9999", "系统异常");
    }

    public static Map<String, Object> error(String code, String desc) {
        return builtRst(code, desc, null);
    }

    public static Map<String, Object> builtRst(String code, String desc, Map<String, Object> data) {
        Map<String, Object> result = Maps.newHashMap();
        result.put(CODE_KEY, code);
        result.put(DESC_KEY, desc);
        if (data == null) {
            data = Maps.newHashMap();
        }
        result.put(DATA_KEY, data);
        return result;
    }
}
