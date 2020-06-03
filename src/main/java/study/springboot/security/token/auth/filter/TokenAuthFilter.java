package study.springboot.security.token.auth.filter;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import study.springboot.security.token.support.redis.RedisClient;
import study.springboot.security.token.support.redis.RedisKeys;
import study.springboot.security.token.support.session.UserInfo;
import study.springboot.security.token.support.session.UserInfoContext;
import study.springboot.security.token.support.utils.JsonUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token认证过滤器
 */
@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final String X_TOKEN = "x-token";

    @Autowired
    private RedisClient redisClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.info(">>>>>> doFilterInternal");
        try {
            //******************** 验证token ********************
            String token = request.getHeader(X_TOKEN);
            if (Strings.isNullOrEmpty(token)) {
                throw new IllegalArgumentException("token为空");
            }
            log.info("token={}", token);
            //******************** 获取用户信息 ********************
            String key = RedisKeys.keyOfToken(token);
            String text = redisClient.get(key);
            if (Strings.isNullOrEmpty(text)) {
                throw new IllegalArgumentException("token过期或错误");
            }
            UserInfo userInfo = JsonUtils.fromJson(text, UserInfo.class);
            UserInfoContext.set(userInfo);
//            //根据token获取username
//            String username = "wzj11";
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, token);
//            if (authentication != null) {
//                SecurityContext securityCtx = SecurityContextHolder.getContext();
//                securityCtx.setAuthentication(authentication);
//            }
            //
            chain.doFilter(request, response);
        } finally {
            UserInfoContext.remove();
        }
    }
}
