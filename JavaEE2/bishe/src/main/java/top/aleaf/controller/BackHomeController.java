/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BackHomeController
 * Author:   郭新晔
 * Date:     2019/2/12 0012 14:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.model.HostHolder;
import top.aleaf.model.Role;
import top.aleaf.model.User;
import top.aleaf.service.RoleService;
import top.aleaf.service.UserService;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.DecryptUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class BackHomeController {
    public static final Logger LOGGER = LoggerFactory.getLogger(BackHomeController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HostHolder hostHolder;

    private String validateCode;

    @RequestMapping(path = {"/", "/index"})
    public String backIndex() {
        return "/user/index";
    }

    @RequestMapping(path = {"/backJumpTo"})
    public String backJumpTo(@RequestParam("path") String path) {
        return path;
    }

    @RequestMapping(path = {"/toLogin"})
    public String login() {
        if (hostHolder.getUser() != null) {
            return "redirect:/index";
        }
        return "login";
    }

    @RequestMapping(path = {"/toRegister"})
    public String register() {
        if (hostHolder.getUser() != null) {
            return "redirect:/index";
        }
        return "mailInput";
    }

    @RequestMapping(path = {"/toRepassword"})
    public String repassword() {
        if (hostHolder.getUser() != null) {
            return "redirect:/index";
        }
        return "repassword";
    }

    @RequestMapping(path = "/back/reg", method = RequestMethod.POST)
    public String reg1(@RequestParam("mail") String mail, Model model) {
        if (hostHolder.getUser() != null) {
            return "redirect:/index";
        }
        Map<String, String> map = this.userService.reg(mail);
        if (map.containsKey("validate")) {
            this.validateCode = map.get("validate");
            model.addAttribute("validateCode", "validateCode");
        } else {
            model.addAttribute("msg", map.get("msg"));
        }
        return "/toRegister";
    }

    @RequestMapping(path = "/back/regValidate", method = RequestMethod.GET)
    public String regValidate(@RequestParam("activeParam") String activeParam, Model model) {
        try {
            if (hostHolder.getUser() != null) {
                return "redirect:/index";
            }
            String param = DecryptUtil.decrypt(activeParam);
            String[] params = param.split(ConstantUtil.MAIL_VALIDATE_SPLIT);
            if (params.length < 2) {
                model.addAttribute("msg", "激活异常");
                return "/toRegister";
            }
            if (this.validateCode != null && this.validateCode.equals(params[0])) {
                model.addAttribute("mail", params[1]);
                return "/register";
            } else {
                model.addAttribute("msg", "激活异常");
                return "/toRegister";
            }
        } catch (Exception e) {
            LOGGER.error("用户激活异常: " + e.getMessage());
            model.addAttribute("msg", "激活异常");
            e.printStackTrace();
            return "/toRegister";
        }
    }

    @RequestMapping(path = "/back/register", method = RequestMethod.POST)
    public String reg2(User user, Model model,
                       HttpServletResponse response) {
        try {
            if (hostHolder.getUser() != null) {
                return "redirect:/index";
            }
            Map<String, String> map = this.userService.register(user);
            if (map.containsKey("ticket")) {
                LOGGER.info("用户注册成功");
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);

                if (initData(user.getMail(), model)) {
                    return "redirect:/index";
                }
            }
            model.addAttribute("msg", map.get("msg"));
            LOGGER.info("用户注册失败");
            return "/toRegister";
        } catch (Exception e) {
            LOGGER.error("用户注册异常: " + e.getMessage());
            model.addAttribute("msg", "注册异常");
            e.printStackTrace();
            return "/toRegister";
        }
    }

    @RequestMapping(path = "/back/login", method = RequestMethod.POST)
    public String login(@RequestParam("mail") String mail, @RequestParam("password") String password,
                        Model model, HttpServletResponse response) {
        try {
            if (hostHolder.getUser() != null) {
                return "redirect:/index";
            }
            Map<String, String> map = this.userService.login(mail, password);
            if (map.containsKey("ticket")) {
                LOGGER.info("用户登录成功");
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);

                if (initData(mail, model)) {
                    return "redirect:/index";
                }
            }
            model.addAttribute("msg", map.get("msg"));
            LOGGER.info("用户登录失败");
            return "/toLogin";
        } catch (Exception e) {
            LOGGER.error("用户登录异常: " + e.getMessage());
            e.printStackTrace();
            return "/toLogin";
        }
    }

    @RequestMapping(path = {"/back/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket, Model model) {
        String msg = "退出成功";
        try {
            if (!this.userService.logout(ticket)) {
                msg = "用户退出失败";
                LOGGER.info("用户退出失败");
            }
        } catch (Exception e) {
            msg = "用户退出异常";
            LOGGER.error("用户退出异常: " + e.getMessage());
            e.printStackTrace();
        }
        model.addAttribute("msg",msg);
        return "loginExit";
    }

    private boolean initData(String mail, Model model) {
        User localUser = this.userService.getByMail(mail);
        if (localUser != null) {
            Role localRole = this.roleService.getByPrimaryKey(localUser.getRoleId());
            model.addAttribute("localUser", localUser);
            model.addAttribute("localRole", localRole);
            return true;
        }
        return false;
    }
}