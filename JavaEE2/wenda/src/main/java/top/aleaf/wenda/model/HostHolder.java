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
package top.aleaf.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @create 2018/12/3 0003
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}