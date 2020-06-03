package study.springboot.security.token.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;


@Component
public class FunctionMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>> {


    @Override
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getObject() throws Exception {
        //初始化
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> funMap = new LinkedHashMap<>();
        //获取受保护功能列表
        //生成映射
        String pfPath = "/res/list";
        //匹配器
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(pfPath + "*");
        //配置属性
        List<ConfigAttribute> configAttrLt = Lists.newArrayList(new SecurityConfig(pfPath));

        funMap.put(matcher, configAttrLt);

        return funMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
