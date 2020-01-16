package study.springboot.security.rest.support.utils;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    public static final String TOKEN_HEADER = "access_token";

    public static boolean isLegal(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return !Strings.isNullOrEmpty(token);
    }

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return token;
    }
}
