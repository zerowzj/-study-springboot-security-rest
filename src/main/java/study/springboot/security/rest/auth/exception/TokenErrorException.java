package study.springboot.security.rest.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenErrorException extends AuthenticationException {

    public TokenErrorException(String msg) {
        super(msg);
    }
}
