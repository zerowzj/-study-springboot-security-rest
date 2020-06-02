package study.springboot.security.token.auth.filter;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token认证过滤器
 */
@Slf4j
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final String X_TOKEN = "x-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.info("======> doFilterInternal");
        String token  = request.getHeader(X_TOKEN);
        if (Strings.isNullOrEmpty(token)) {
//            chain.doFilter(request, response);
            throw new IllegalArgumentException("token错误");
        }
        log.info("token={}", token);
//        final String auth_token_start = "Bearer ";
//        if (Strings.isNotEmpty(token) && token.startsWith(auth_token_start)) {
//            token = token.substring(auth_token_start.length());
//        } else {
//            // 不按规范,不允许通过验证
//            token = null;
//        }
//        String username = jwtUtils.getUsernameFromToken(auth_token);
//        logger.info(String.format("Checking authentication for user %s.", username));
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            User user = jwtUtils.getUserFromToken(auth_token);
//            if (jwtUtils.validateToken(auth_token, user)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                logger.info(String.format("Authenticated user %s, setting security context", username));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        chain.doFilter(request, response);
    }
}
