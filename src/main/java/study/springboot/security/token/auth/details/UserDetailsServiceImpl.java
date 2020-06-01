package study.springboot.security.token.auth.details;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("======> loadUserByUsername");
        if (Strings.isNullOrEmpty(username) || !"wzj".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("未找到用户信息");
        }
        CustomUserDetails userDetails = new CustomUserDetails("wzj", "123");
        return userDetails;
    }
}
