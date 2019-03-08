package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.model.HostHolder;
import top.aleaf.model.Role;
import top.aleaf.model.User;
import top.aleaf.model.ViewObject;
import top.aleaf.service.RoleService;
import top.aleaf.service.UserService;
import top.aleaf.utils.BisheUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/user"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg, User user, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            List<User> userList = this.userService.getAll(user);
            List<ViewObject> vos = new ArrayList<>();
            for (User item : userList) {
                ViewObject vo = new ViewObject();
                vo.set("user", item);
                vo.set("roleName", roleService.getByPrimaryKey(item.getRoleId()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<User>(userList));
            model.addAttribute("user", user);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("用户列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/user";
    }

    @RequestMapping(path = {"/user/index"})
    public String home(Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localUser != null) {
                model.addAttribute("localUser", localUser);
                model.addAttribute("localRole", localRole);
                return localRole.getDetail() + "/index";
            }
        } catch (Exception e) {
            LOGGER.error("用户主页加载出错");
            e.printStackTrace();
        }
        return "redirect:/toLogin";
    }

    @RequestMapping(path = {"/user/edit"}, method = RequestMethod.POST)
    public String editRole(User user, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            boolean isSave = user.getId() == null;

            msg = this.userService.save(user) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("用户编辑出错");
            msg = "用户编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/user";
    }

    @RequestMapping(path = {"/user/forEdit"})
    public String forUpdate(@RequestParam(value = "userId", required = false) Integer userId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            if (userId != null) {
                User user = this.userService.getByPrimaryKey(userId);
                model.addAttribute("user", user);
            }
            List<Role> roleList = this.roleService.getAll(new Role());
            model.addAttribute("roleList", roleList);
        } catch (Exception e) {
            LOGGER.error("帐号数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/userEdit";
    }

    @RequestMapping(path = {"/user/delete/{userId}"})
    public String delete(@PathVariable("userId") int userId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            msg = this.userService.delete(userId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("用户删除出错");
            msg = "用户删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/user";
    }

    @RequestMapping(path = "/user/setting")
    public String userSetting(@RequestParam(value = "oldPassword", required = false) String oldPassword,
                              User user,
                              Model model) {
        String msg = null;
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            model.addAttribute("localUser", localUser);
            model.addAttribute("localRole", localRole);

            if (oldPassword != null) {
                if (this.userService.validatePwd(localUser, oldPassword)) {
                    user.setPassword(BisheUtil.MD5(user.getPassword() + localUser.getSalt()));
                    msg = this.userService.save(user) ? "修改密码成功" : "修改密码失败";
                }
                msg = "密码输入错误";
            } else {
                if (user.getId() != null) {
                    msg = this.userService.save(user) ? "保存成功" : "保存失败";
                    model.addAttribute("localUser", this.userService.getByPrimaryKey(user.getId()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("用户信息设置出错");
            msg = "用户信息设置出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "smanager/edit/userSetting";
    }

}