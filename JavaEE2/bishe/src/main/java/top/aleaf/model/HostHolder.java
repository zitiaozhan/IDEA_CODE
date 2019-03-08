/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HostHolder
 * Author:   郭新晔
 * Date:     2018/12/3 0003 15:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> user = new ThreadLocal<>();
    private static ThreadLocal<Role> role = new ThreadLocal<>();

    public User getUser() {
        return user.get();
    }

    public Role getRole() {
        return role.get();
    }

    public void setUser(User setUser) {
        user.set(setUser);
    }

    public void setRole(Role setRole) {
        role.set(setRole);
    }

    public void clear() {
        user.remove();
        role.remove();
    }
}