package study.springboot.security.token.support.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import study.springboot.security.token.support.result.Result;

import java.io.InputStream;

@Slf4j
public class JsonUtils {

    /**
     * Object ==> String
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * String ==> Object
     */
    public static <T> T fromJson(String text, Class<T> clazz) {
        T obj;
        try {
            obj = JSON.parseObject(text, clazz);
        } catch (Exception ex) {
            log.error("", ex);
            throw ex;
        }
        return obj;
    }

    public static <T> T fromJson(InputStream is, Class<T> clazz) {
        T obj;
        try {
            obj = JSON.parseObject(is, clazz);
        } catch (Exception ex) {
            log.error("", ex);
            throw new RuntimeException();
        }
        return obj;
    }
}
