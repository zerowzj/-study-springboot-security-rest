package study.springboot.security.token.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import study.springboot.security.token.auth.details.UserDetailsServiceImpl;
import study.springboot.security.token.auth.filter.RestLoginFilter;
import study.springboot.security.token.auth.filter.TokenAuthFilter;

@Component
public class AuthenticationCfg {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * 登录过滤器
     */
    @Bean
    public RestLoginFilter restLoginFilter() {
        return new RestLoginFilter();
    }

    /**
     * Token认证过滤器
     */
    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter();
    }
}
