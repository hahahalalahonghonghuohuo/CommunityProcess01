package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Author: yyp
 * @Date: 2024/4/10 - 04 - 10 - 15:27
 * @Description: com.nowcoder.community.util 持有用户信息, 用于代替 session 对象
 * @version: 1.0
 */

@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    /** 请求结束时清理掉 ThreadLocal 中的 User , 否则会占用太多内存*/
    public void clear() {
        users.remove();
    }


}
