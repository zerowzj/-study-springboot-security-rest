package study.springboot.security.token.auth.entrypoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import study.springboot.security.token.support.result.Results;
import study.springboot.security.token.support.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException, ServletException {
       log.info("RestAuthenticationEntryPoint");
        //
        log.info("status={}", response.getStatus());
        response.setStatus(200);
        if (ex instanceof BadCredentialsException) {

        } else if (ex instanceof UsernameNotFoundException) {

        } else {
            WebUtils.write(response,null);
        }
    }
}
