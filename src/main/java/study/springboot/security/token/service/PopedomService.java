package study.springboot.security.token.service;

import java.util.List;

public interface PopedomService {

    List<String> getFunctionLt();

    List<String> getFunctionLt(Long userId);
}
