package study.springboot.security.token.support.utils;

import java.util.UUID;

public final class TokenGenerator {

    private TokenGenerator() {
    }

    public static String createToken() {
        return UUID.randomUUID().toString();
    }
}
