package study.springboot.security.token.auth.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String username;

    private String password;
}
