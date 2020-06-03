package study.springboot.security.token.auth.filter;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token认证过滤器
 */
@Slf4j
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final String X_TOKEN = "x-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.info(">>>>>> doFilterInternal");
        String token = request.getHeader(X_TOKEN);
        if (Strings.isNullOrEmpty(token)) {
//            chain.doFilter(request, response);
            throw new IllegalArgumentException("token错误");
        }
        log.info("token={}", token);
        //根据token获取username
        String username = "wzj11";
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, token);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
