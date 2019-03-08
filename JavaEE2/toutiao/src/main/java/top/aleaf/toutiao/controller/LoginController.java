/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LoginController
 * Author:   郭新晔
 * Date:     2018/11/24 0024 20:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.aleaf.toutiao.model.EntityType;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.service.UserService;
import top.aleaf.toutiao.sync.EventModel;
import top.aleaf.toutiao.sync.EventProducer;
import top.aleaf.toutiao.sync.EventType;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2018/11/24 0024
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam(value = "username") String userName,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "rember", defaultValue = "0") int rememberMe,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = this.userService.registerUser(userName, password);
            if (map.containsKey("lineNum")) {
                //添加登录cookie信息
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");    //设置cookie的有效范围为全站
                if (rememberMe > 0) {
                    cookie.setMaxAge(3600 * 24 * 7);    //cookie保存一周时间
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0, "注册成功");
            }
            logger.error("注册异常：" + map.toString());
            return ToutiaoUtil.getJSONString(1, map);
        } catch (Exception e) {
            logger.error("注册异常：" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam(value = "username") String userName,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rememberMe,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = this.userService.loginUser(userName, password);
            if (map.containsKey("ticket")) {
                //添加登录cookie信息
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");    //设置cookie的有效范围为全站
                if (rememberMe > 0) {
                    cookie.setMaxAge(3600 * 24 * 7);    //cookie保存一周时间
                }
                response.addCookie(cookie);

                User user = this.userService.getUserByName(userName);
                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                        .setEntityType(EntityType.USER)
                        .setExt("eventDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                        .setActor(user.getId()).setEntityOwnerId(user.getId()));

                return ToutiaoUtil.getJSONString(0, "登录成功");
            }
            logger.error("登录异常：" + map.toString());
            return ToutiaoUtil.getJSONString(1, map);
        } catch (Exception e) {
            logger.error("登录异常：" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "登录异常");
        }
    }

    @RequestMapping(path = {"/logout"})
    public String logout(@CookieValue("ticket") String ticket) {
        this.userService.logout(ticket);
        return "redirect:/index";
    }
}