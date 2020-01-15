package study.springboot.security.rest.auth;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.springboot.security.rest.auth.entrypoint.JwtAuthenticationEntryPoint;
import study.springboot.security.rest.auth.filter.RestAuthenticationFilter;
import study.springboot.security.rest.auth.filter.RestLoginFilter;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JwtLoginFilter，JwtAuthFilter组合在一起
 */
@Configuration
@EnableWebSecurity  //启用web安全检查
//@EnableGlobalMethodSecurity(prePostEnabled = true) //启用全局方法的安全检查（预处理预授权的属性为true）
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {

    public WebSecurityCfg(){
        super(true);
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //
        web.debug(true);
        //
        web.ignoring()
                .antMatchers("/demo");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //CSRF保护（▲）CsrfFilter
        http.csrf()
                .disable();
        //会话管理（▲）SessionManagementFilter
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .disable();
        //异常处理（▲）ExceptionTranslationFilter
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .disable();
        //（▲）FilterSecurityInterceptor
//        http.authorizeRequests()
//               // .antMatchers("/login", "/demo").permitAll()
//                .anyRequest().authenticated();
        //
        http.addFilterAt(new RestLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new RestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //
        http.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl()
                .disable();
        //（▲）LogoutFilter
        http.logout()
                .disable();
        //（▲）AnonymousAuthenticationFilter
        http.anonymous()
                .disable();
    }

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
}