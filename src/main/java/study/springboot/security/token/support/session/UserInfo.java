package study.springboot.security.token.support.session;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserInfo implements Serializable {

    private Long userId;
}
