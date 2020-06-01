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

    public WebSecurityCfg() {
        super(true);
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * ====================
     * <p>
     * ====================
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //
        web.debug(true);
        //
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
        //（▲）CsrfFilter
        http.csrf()
                .disable();
        //（▲）SessionManagementFilter
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //（▲）FilterSecurityInterceptor
//        http.authorizeRequests()
//               // .antMatchers("/login", "/demo").permitAll()
//                .anyRequest().authenticated();
        //
        http.addFilter(new RestLoginFilter(authenticationManager()))
                .addFilterAfter(new TokenAuthFilter(), RestLoginFilter.class);
        //
        http.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl()
                .disable();
        //（▲）LogoutFilter
        http.logout();
        //（▲）AnonymousAuthenticationFilter
        http.anonymous();
        //（▲）ExceptionTranslationFilter
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * ====================
     * 认证管理器
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