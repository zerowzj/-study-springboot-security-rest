package study.springboot.security.token.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import study.springboot.security.token.auth.filter.RestLoginFilter;
import study.springboot.security.token.auth.filter.TokenAuthFilter;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JwtLoginFilter，JwtAuthFilter组合在一起
 */
@Configuration
//@EnableWebSecurity  //启用web安全检查
//@EnableGlobalMethodSecurity(prePostEnabled = true) //启用全局方法的安全检查（预处理预授权的属性为true）
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenAuthFilter tokenAuthFilter;
    @Autowired
    private RestLoginFilter restLoginFilter;

    @Autowired
    private ObjectPostProcessor objectPostProcessor;

    public WebSecurityCfg() {
        super(true);
    }

    /**
     * ====================
     * 配置
     * ====================
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //（▲）Session管理，SessionManagementFilter
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //（▲）自定义过滤器
        http.addFilter(restLoginFilter)
                .addFilterAfter(tokenAuthFilter, RestLoginFilter.class);


        //（▲）SecurityContextPr
//        http.securityContext()
//                .securityContextRepository(null);
        //（▲）跨域，CsrfFilter
//        http.csrf()
//                .disable();

        //（▲）FilterSecurityInterceptor
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .withObjectPostProcessor(objectPostProcessor);
        //（▲）头部
//        http.headers()
//                .frameOptions()
//                .sameOrigin()
//                .cacheControl()
//                .disable();
        //（▲）注销，LogoutFilter
//        http.logout();
//        //（▲）匿名，AnonymousAuthenticationFilter
//        http.anonymous();
//        //（▲）异常处理，ExceptionTranslationFilter
//        http.exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint);
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
     * 认证管理器配置
     * ====================
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }



}