package study.springboot.security.token.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorizationCfg {

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

    /**
     * 角色投票器
     */
    @Bean
    public RoleVoter roleVoter() {
        return new RoleVoter();
    }

    /**
     * 基于肯定的访问决策器
     */
    @Bean
    public AccessDecisionManager affirmativeBased(List<AccessDecisionVoter<?>> decisionVoters) {
        return new AffirmativeBased(decisionVoters);
    }
}
