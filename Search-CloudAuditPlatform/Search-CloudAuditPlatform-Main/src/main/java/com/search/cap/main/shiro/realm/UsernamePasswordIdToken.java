package com.search.cap.main.shiro.realm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by heyanjing on 2018/8/20 11:03.
 */
@Getter
@Setter
public class UsernamePasswordIdToken extends UsernamePasswordToken {

    /**
     * 用户id
     */
    private String sid;

    public UsernamePasswordIdToken(String sid) {
        this.sid = sid;
    }

    public UsernamePasswordIdToken(String username, String password) {
        super(username, password);
    }
}
