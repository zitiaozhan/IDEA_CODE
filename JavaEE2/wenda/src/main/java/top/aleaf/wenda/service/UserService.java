/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   郭新晔
 * Date:     2019/1/18 0018 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.mapper.LoginTicketMapper;
import top.aleaf.wenda.mapper.UserMapper;
import top.aleaf.wenda.model.LoginTicket;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.util.GeneralExample;
import top.aleaf.wenda.util.WendaUtil;

import java.util.*;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper ticketMapper;

    public List<User> getAll(User user) {
        if (user.getPage() != null && user.getRows() != null) {
            //PageHelper.startPage(user.getPage(), user.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (user.getName() != null && user.getName().length() > 0) {
            builder.append("and name like %").append(user.getName()).append("%");
        }
        return userMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                User.class, builder.toString(), true
        ));
    }

    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getByName(String name) {
        return userMapper.selectOne(new User().setName(name));
    }

    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public void saveOrUpdate(User user) {
        if (user.getId() != null) {
            userMapper.updateByPrimaryKey(user);
        } else {
            userMapper.insert(user);
        }
    }

    public Map<String, String> register(String userName, String password) {
        Map<String, String> map = new HashMap<>();

        if (userName == null || userName.trim().length() <= 0) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (password == null || password.trim().length() <= 0) {
            map.put("msg", "密码不能为空");
            return map;
        }
        if (password.length() < 6) {
            map.put("msg", "密码长度必须5位以上");
            return map;
        }
        if (this.userMapper.selectOne(new User().setName(userName)) != null) {
            map.put("msg", "该用户名已被注册");
            return map;
        }
        User user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(1, 8));
        Random random = new Random();
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        int lineNum = this.userMapper.insert(user);

        if (lineNum > 0) {
            String ticket = this.addLoginTicket(user.getId());
            map.put("ticket", ticket);
        }

        return map;
    }

    public Map<String, String> login(String userName, String password) {
        Map<String, String> map = new HashMap<>();

        if (userName == null || userName.trim().length() <= 0) {
            map.put("msg", "用户名为空");
            return map;
        }
        if (password == null || password.trim().length() <= 0) {
            map.put("msg", "密码为空");
            return map;
        }
        User user = new User();
        user.setName(userName);
        User us = this.userMapper.selectOne(user);
        if (WendaUtil.MD5(password + us.getSalt()).equals(us.getPassword())) {
            String ticket = this.addLoginTicket(us.getId());
            map.put("ticket", ticket);
            map.put("userId", String.valueOf(us.getId()));
        }

        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date date = new Date();
        date.setTime(date.getTime() + 3 * 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticketMapper.insert(ticket);
        return ticket.getTicket();
    }

    public boolean logout(String ticket) {
        LoginTicket loginTicket = this.ticketMapper.selectOne(new LoginTicket().setTicket(ticket));
        loginTicket.setStatus(1);
        return this.ticketMapper.updateByPrimaryKey(loginTicket) > 0;
    }

}