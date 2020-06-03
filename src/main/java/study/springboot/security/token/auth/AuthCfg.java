package study.springboot.security.token.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import study.springboot.security.token.auth.details.UserDetailsServiceImpl;
import study.springboot.security.token.auth.filter.RestLoginFilter;
import study.springboot.security.token.auth.filter.TokenAuthFilter;

import java.util.List;

@Configuration
public class AuthCfg {

    /**
     * ====================
     * 认证
     * ====================
     */
    //用户详情
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    //登录过滤器
    @Bean
    public RestLoginFilter restLoginFilter() {
        return new RestLoginFilter();
    }

    //Token认证过滤器
    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter();
    }

    /**
     * ====================
     * 授权
     * ====================
     */
    //角色投票器
    @Bean
    public RoleVoter roleVoter() {
        return new RoleVoter();
    }

    //基于肯定的访问决策器
    @Bean
    public AccessDecisionManager affirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
        return new AffirmativeBased(decisionVoters);
    }

    /**
     * RequestMatcher 生成器
     */
//    @Bean
//    public RequestMatcherCreator requestMatcherCreator() {
//        return metaResources -> metaResources.stream()
//                .map(metaResource -> new AntPathRequestMatcher(metaResource.getPattern(), metaResource.getMethod()))
//                .collect(Collectors.toSet());
//    }
//
//    /**
//     * 元数据加载器
//     */
//    @Bean
//    public FilterInvocationSecurityMetadataSource dynamicFilterInvocationSecurityMetadataSource() {
//        return new DynamicFilterInvocationSecurityMetadataSource();
//    }

}
