package study.springboot.security.token.support.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public final class Result<T> implements Serializable {

    private String code;

    private String desc;

    private T data;

    protected Result(T data) {
        this(Results.SUCC_CODE, Results.SUCC_DESC);
        this.data = data;
    }

    protected Result(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
