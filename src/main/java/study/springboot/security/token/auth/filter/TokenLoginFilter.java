package study.springboot.security.token.auth.filter;

import com.google.common.collect.Lists;
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
import study.springboot.security.token.auth.details.TokenUserDetails;
import study.springboot.security.token.support.redis.RedisClient;
import study.springboot.security.token.support.redis.RedisKeys;
import study.springboot.security.token.support.result.Result;
import study.springboot.security.token.support.result.Results;
import study.springboot.security.token.support.session.UserInfo;
import study.springboot.security.token.support.utils.CookieUtils;
import study.springboot.security.token.support.utils.JsonUtils;
import study.springboot.security.token.support.utils.TokenGenerator;
import study.springboot.security.token.support.utils.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 认证
 * （1）验证用户名密码正确后，生成一个token并返回给客户端
 * （2）该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication：接收并解析用户凭证。
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private RedisClient redisClient;

    public TokenLoginFilter() {
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * ====================
     * 认证
     * ====================
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info(">>>>>> 尝试认证");
        //******************* <1>. ********************
        InputStream text = WebUtils.getBodyStream(request);
        LoginRequest loginRequest = JsonUtils.fromJson(text, LoginRequest.class);
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        //******************* <2>. ********************
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
                password,
                Lists.newArrayList());
        return getAuthenticationManager().authenticate(token);
    }

    /**
     * ====================
     * 认证成功
     * ====================
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info(">>>>>> 认证成功");
        //******************** <1>.获取用户详情 ********************
        TokenUserDetails userDetails = (TokenUserDetails) authentication.getPrincipal();

        //******************** <2>.保存用户信息 ********************
        //生成token
        String token = TokenGenerator.createToken();
        String key = RedisKeys.keyOfToken(token);
        //用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(900001L);
        userInfo.setUsername("王振军");
        //存储
        redisClient.set(key, JsonUtils.toJson(userInfo), 60 * 1000);

        //******************** <3>.设置Cookie ********************
        response.addCookie(CookieUtils.newCookie("token", token));

        //******************** <4>.返回 ********************
        WebUtils.write(response, Results.success());
    }

    /**
     * ====================
     * 认证失败
     * ====================
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException ex) throws IOException, ServletException {
        log.info(">>>>>> 认证失败", ex);
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
