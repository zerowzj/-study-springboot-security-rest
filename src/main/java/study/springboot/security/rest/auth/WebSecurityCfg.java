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
import study.springboot.security.rest.auth.entrypoint.RestAuthenticationEntryPoint;
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
    private RestAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //
        web.debug(true);
        //
        web.ignoring();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //（▲）SecurityContextPr
//        http.securityContext()
//                .securityContextRepository(null);
        //（▲）CsrfFilter
        http.csrf()
                .disable();
        //（▲）SessionManagementFilter
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //（▲）FilterSecurityInterceptor
//        http.authorizeRequests()
//               // .antMatchers("/login", "/demo").permitAll()
//                .anyRequest().authenticated();
        //
        http.addFilter(new RestLoginFilter(authenticationManager()))
                .addFilterAfter(new RestAuthenticationFilter(), RestLoginFilter.class);
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
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
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