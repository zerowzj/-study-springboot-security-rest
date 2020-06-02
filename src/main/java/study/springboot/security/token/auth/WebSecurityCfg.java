package study.springboot.security.token.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import study.springboot.security.token.auth.entrypoint.RestAuthenticationEntryPoint;
import study.springboot.security.token.auth.filter.RestLoginFilter;
import study.springboot.security.token.auth.filter.TokenAuthFilter;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JwtLoginFilter，JwtAuthFilter组合在一起
 */
@Configuration
@EnableWebSecurity  //启用web安全检查
//@EnableGlobalMethodSecurity(prePostEnabled = true) //启用全局方法的安全检查（预处理预授权的属性为true）
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestLoginFilter restLoginFilter;
    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    public WebSecurityCfg() {
        super(true);
    }

    /**
     * ====================
     * <p>
     * ====================
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
        web.ignoring();
    }

    /**
     * ====================
     * 配置
     * ====================
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //（▲）SecurityContextPr
//        http.securityContext()
//                .securityContextRepository(null);
        //（▲）跨域，CsrfFilter
        http.csrf()
                .disable();
        //（▲）Session管理，SessionManagementFilter
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
        //（▲）FilterSecurityInterceptor
//        http.authorizeRequests()
//               // .antMatchers("/login", "/demo").permitAll()
//                .anyRequest().authenticated();
        //（▲）自定义过滤器
        http.addFilter(restLoginFilter)
                .addFilterAfter(tokenAuthFilter, RestLoginFilter.class);
        //（▲）头部
        http.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl()
                .disable();
        //（▲）注销，LogoutFilter
        http.logout();
        //（▲）匿名，AnonymousAuthenticationFilter
        http.anonymous();
        //（▲）异常处理，ExceptionTranslationFilter
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * ====================
     * 认证管理器配置
     * ====================
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /**
     * ====================
     * 认证管理器
     * ====================
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}