/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserService
 * Author:   郭新晔
 * Date:     2018/11/22 0022 22:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.dao.LoginTicketDAO;
import top.aleaf.toutiao.dao.UserDAO;
import top.aleaf.toutiao.model.LoginTicket;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.utils.CheckUser;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import java.util.*;

/**
 * 〈〉
 *
 * @create 2018/11/22 0022
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO ticketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public User getUserByName(String name) {
        return userDAO.selectByName(name);
    }

    public Map<String, Object> registerUser(String userName, String password) {
        Map<String, Object> resMap = new HashMap<>();
        //用户名是否为空、是否重复
        if (StringUtils.isBlank(userName)) {
            resMap.put("msgname", "用户名不能为空！");
            return resMap;
        } else if (this.userDAO.selectByName(userName) != null) {
            resMap.put("msgname", "用户名已被注册！");
            return resMap;
        } else if (!CheckUser.checkUserName(userName)) {
            resMap.put("msgname", "用户名不合法！");
            return resMap;
        }
        //密码是否为空
        if (StringUtils.isBlank(password)) {
            resMap.put("msgpwd", "密码不能为空！");
            return resMap;
        } else if (password.length() < 6) {
            resMap.put("msgpwd", "密码长度不得小于6！");
            return resMap;
        }
        //密码强度
        //resMap.put("pwdPower", CheckUser.checkPassword(password));

        //保存信息
        User user = new User();
        user.setName(userName);
        //随机生成唯一的的 UUID（表示通用唯一标识符 (UUID)，一个 128 位的值）
        user.setSalt(UUID.randomUUID().toString().substring(1, 6));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password + user.getSalt()));
        int lineNum = userDAO.addUser(user);
        if (lineNum > 0) {
            resMap.put("lineNum", lineNum);

            //ticket
            String ticket = addLoginTicket(user.getId());
            resMap.put("ticket", ticket);
        }

        return resMap;
    }

    public Map<String, Object> loginUser(String userName, String password) {
        Map<String, Object> resMap = new HashMap<>();
        //用户名是否为空
        if (StringUtils.isBlank(userName)) {
            resMap.put("msgname", "用户名不能为空！");
            return resMap;
        }
        //密码是否为空
        if (StringUtils.isBlank(password)) {
            resMap.put("msgpwd", "密码不能为空！");
            return resMap;
        }

        User user = userDAO.selectByName(userName);
        if (user == null) {
            resMap.put("msgname", "用户名不正确！");
            return resMap;
        }
        if (!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            resMap.put("msgpwd", "密码不正确！");
            return resMap;
        }

        //ticket
        String ticket = addLoginTicket(user.getId());
        resMap.put("ticket", ticket);

        return resMap;
    }

    public void updatePassword(User user) {
        userDAO.updatePassword(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteById(id);
    }

    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticketDAO.addLoginTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        this.ticketDAO.updateLoginTicket(ticket, 1);
    }
}