package study.springboot.security.token.auth;

import java.util.UUID;

public final class TokenGenerator {

    private TokenGenerator() {
    }

    public static String createToken() {
        return UUID.randomUUID().toString();
    }
}
