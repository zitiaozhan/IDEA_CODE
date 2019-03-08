/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserController
 * Author:   郭新晔
 * Date:     2019/1/18 0018 20:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.wenda.service.SensitiveFilterService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventProducer;
import top.aleaf.wenda.util.DecryptUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private SensitiveFilterService sensitiveFilterService;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String reg(@RequestParam("userName") String userName,
                      @RequestParam("password") String password,
                      @RequestParam("rememberme") boolean rememberme,
                      @RequestParam(value = "next", required = false) String next,
                      Model model, HttpServletResponse response) {
        try {
            userName = sensitiveFilterService.filterSensitiveWord(userName);
            model.addAttribute("next", next == null ? "2f696e646578" : next);
            Map<String, String> map = this.userService.register(userName, password);
            if (map.containsKey("ticket")) {
                LOGGER.info("用户注册成功");
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 7);
                }
                response.addCookie(cookie);
                if (next != null) {
                    next = DecryptUtil.decrypt(next);
                    if (!next.contains("http") && next.trim().length() > 0) {
                        return "redirect:" + next;
                    }
                }
                return "redirect:/index";
            }
            model.addAttribute("msg", map.get("msg"));
            LOGGER.info("用户注册失败");
            return "login";
        } catch (Exception e) {
            LOGGER.error("用户注册异常: " + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("rememberme") boolean rememberme,
                        @RequestParam(value = "next", required = false) String next,
                        Model model, HttpServletResponse response) {
        try {
            model.addAttribute("next", next == null ? "2f696e646578" : next);
            Map<String, String> map = this.userService.login(userName, password);
            if (map.containsKey("ticket")) {
                LOGGER.info("用户登录成功");
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 7);
                }
                response.addCookie(cookie);

                //登录异常--管理邮件发送
                /*EventModel eventModel = new EventModel();
                eventModel.setEventType(EventType.LOGIN)
                        .setEventOwnerId(Integer.parseInt(map.get("userId")))
                        .setEntityType(EntityType.ENTITY_ADMIN_MAIL.getValue())
                        .setEntityId(WendaUtil.ENTITY_MAIL)
                        .setActorId(WendaUtil.ADMIN_USER);
                this.eventProducer.fireEvent(eventModel);*/

                if (next != null) {
                    next = DecryptUtil.decrypt(next);
                    if (!next.contains("http") && next.trim().length() > 0) {
                        return "redirect:" + next;
                    }
                }

                return "redirect:/index";
            }
            model.addAttribute("msg", map.get("msg"));
            LOGGER.info("用户登录失败");
            return "login";
        } catch (Exception e) {
            LOGGER.error("用户登录异常: " + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        try {
            if (!this.userService.logout(ticket)) {
                LOGGER.info("用户退出失败");
            }
            return "login";
        } catch (Exception e) {
            LOGGER.error("用户退出异常: " + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = {"/forLogin"}, method = {RequestMethod.GET})
    public String forLogin(@RequestParam(value = "next", required = false) String next,
                           Model model) {
        if (next != null) {
            model.addAttribute("next", next);
        }
        return "login";
    }
}