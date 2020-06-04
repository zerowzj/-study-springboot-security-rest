package study.springboot.security.token.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import study.springboot.security.token.auth.details.UserDetailsServiceImpl;
import study.springboot.security.token.auth.filter.RestLoginFilter;
import study.springboot.security.token.auth.filter.TokenAuthFilter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
public class AuthCfg {

    /**
     * ====================
     * （★）认证相关
     * ====================
     */
    //用户详情
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService detailsService = new UserDetailsServiceImpl();
        return detailsService;
    }

    //登录过滤器
    @Bean
    public RestLoginFilter restLoginFilter() {
        RestLoginFilter filter = new RestLoginFilter();
        return filter;
    }

    //Token认证过滤器
    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        TokenAuthFilter filter = new TokenAuthFilter();
        return filter;
    }

    /**
     * ====================
     * （★）授权相关
     * ====================
     */
    //角色投票器
    @Bean
    public RoleVoter roleVoter() {
        RoleVoter voter = new RoleVoter();
        voter.setRolePrefix("");
        return voter;
    }

    //基于肯定的访问决策器
    @Bean
    public AccessDecisionManager affirmativeBased(List<AccessDecisionVoter<?>> decisionVoterLt) {
        return new AffirmativeBased(decisionVoterLt);
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

    /**
     * 元数据加载器
     */
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> f) {
        return new DefaultFilterInvocationSecurityMetadataSource(f);
    }

    /**
     * 自定义 FilterSecurityInterceptor  ObjectPostProcessor 以替换默认配置达到动态权限的目的
     */
//    @Bean
//    public ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor(AccessDecisionManager accessDecisionManager,
//                                                                                                       FilterInvocationSecurityMetadataSource metadataSource) {
//        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
//            @Override
//            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                object.setAccessDecisionManager(accessDecisionManager);
//                object.setSecurityMetadataSource(metadataSource);
//                return object;
//            }
//        };
//    }
}
