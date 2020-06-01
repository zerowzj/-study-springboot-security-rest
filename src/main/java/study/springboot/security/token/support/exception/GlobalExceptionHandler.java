package study.springboot.security.token.support.exception;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public Map<String, Object> resolveException(Exception ex) {
        log.error("发生异常====>", ex);
        Map<String, Object> data = Maps.newHashMap();
        data.put("code", "9999");
        data.put("desc", ex.getMessage());
        return data;
    }
}
