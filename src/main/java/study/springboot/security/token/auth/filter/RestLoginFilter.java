package study.springboot.security.token.auth.filter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.springboot.security.token.auth.details.CustomUserDetails;
import study.springboot.security.token.support.result.Result;
import study.springboot.security.token.support.result.Results;
import study.springboot.security.token.support.utils.JsonUtils;
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
public class RestLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public RestLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("======> attemptAuthentication");
        InputStream is = WebUtils.getBodyStream(request);
        CustomUserDetails userDetails = JsonUtils.fromJson(is, CustomUserDetails.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                Lists.newArrayList());
        return authenticationManager.authenticate(token);
    }

    /**
     * 成功认证后处理
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("======> successfulAuthentication");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = "666666666";
        //
        WebUtils.write(response, Results.success());
    }

    /**
     * 失败认证后处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException ex) throws IOException, ServletException {
        log.info("======> unsuccessfulAuthentication", ex);
        Result result;
        if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
            result = Results.fail("3001", "用户名或密码错误");
        } else {
            result = Results.fail("9999", "系统异常");
        }
        WebUtils.write(response, result);
    }
}
