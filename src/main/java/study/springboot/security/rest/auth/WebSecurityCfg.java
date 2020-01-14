package study.springboot.security.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        http.headers()
                .frameOptions()
                .sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();

        http
                //禁用CSRF保护
                .csrf().disable()
                .authorizeRequests()
                //任何访问都必须授权
                .anyRequest().fullyAuthenticated()
                //配置那些路径可以不用权限访问
                .mvcMatchers("/login").permitAll()
//                .and()
//                .formLogin()
//                //登陆成功后的处理，因为是API的形式所以不用跳转页面
//                .successHandler(new RestAuthenticationSuccessHandler())
//                //登陆失败后的处理
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                //登出后的处理
//                .logout().logoutSuccessHandler(new RestLogoutSuccessHandler())
//                .and()
//                //认证不通过后的处理
//                .exceptionHandling()
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        ;
//        //异常处理
//        http.exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
//        //
        http.addFilter(new RestLoginFilter(authenticationManager()))
                .addFilterAfter(new RestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .mvcMatchers("/login", "/demo").permitAll();
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