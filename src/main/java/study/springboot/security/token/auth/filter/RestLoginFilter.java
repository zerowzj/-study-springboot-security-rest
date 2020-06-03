package study.springboot.security.token.auth.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import study.springboot.security.token.auth.details.CustomUserDetails;
import study.springboot.security.token.support.redis.RedisClient;
import study.springboot.security.token.support.redis.RedisKeys;
import study.springboot.security.token.support.result.Result;
import study.springboot.security.token.support.result.Results;
import study.springboot.security.token.support.session.UserInfo;
import study.springboot.security.token.support.utils.JsonUtils;
import study.springboot.security.token.support.utils.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 认证
 * （1）验证用户名密码正确后，生成一个token并返回给客户端
 * （2）该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication：接收并解析用户凭证。
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
@Slf4j
@Component
public class RestLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private RedisClient redisClient;
//
//    private AuthenticationManager authenticationManager;

    public RestLoginFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    /**
     * ====================
     * 认证
     * ====================
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info(">>>>>> attemptAuthentication");
        //
        InputStream text = WebUtils.getBodyStream(request);
        LoginRequest loginRequest = JsonUtils.fromJson(text, LoginRequest.class);
        //
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword(),
                Lists.newArrayList());
        return getAuthenticationManager().authenticate(token);
    }

    /**
     * ====================
     * 认证成功后
     * ====================
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info(">>>>>> successfulAuthentication");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //******************** 生成token ********************
        String token = "666666666";
        String key = RedisKeys.keyOfToken(token);
        UserInfo userInfo = new UserInfo();
        redisClient.set(key, JsonUtils.toJson(userInfo));
        //返回
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        WebUtils.write(response, Results.success(data));
    }

    /**
     * ====================
     * 认证失败后
     * ====================
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException ex) throws IOException, ServletException {
        log.info(">>>>>> unsuccessfulAuthentication", ex);
        Result result;
        if (ex instanceof UsernameNotFoundException ||
                ex instanceof BadCredentialsException) {
            result = Results.fail("3001", "用户名或密码错误");
        } else {
            result = Results.fail("9999", "系统异常");
        }
        WebUtils.write(response, result);
    }
}
