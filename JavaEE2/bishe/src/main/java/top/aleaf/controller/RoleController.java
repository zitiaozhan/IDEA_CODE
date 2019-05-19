package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.model.HostHolder;
import top.aleaf.model.Role;
import top.aleaf.model.ViewObject;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.service.RoleService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/12 0012
 */
@Controller
public class RoleController {
    public static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    @Resource
    private RoleService roleService;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/role"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Role role, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            List<Role> roleList = this.roleService.getAll(role);
            ViewObject vo = new ViewObject();
            vo.set("roleList", roleList);
            vo.set("nowDate", new Date());
            model.addAttribute("vo", vo);
            model.addAttribute("msg", msg);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(roleList));
            model.addAttribute("role", role);

            model.addAttribute("msg", msg);
        } catch (Exception e) {
            LOGGER.error("角色列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/role";
    }

    @RequestMapping(path = {"/role/edit"}, method = RequestMethod.POST)
    public String editRole(Role role, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            boolean isSave = role.getId() == null;
            msg = this.roleService.save(role) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("角色编辑出错");
            msg = "角色编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/role";
    }

    @RequestMapping(path = {"/role/forEdit"})
    public String forUpdate(@RequestParam(value = "roleId", required = false) Integer roleId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            if (roleId != null) {
                Role role = this.roleService.getByPrimaryKey(roleId);
                model.addAttribute("role", role);
            }
        } catch (Exception e) {
            LOGGER.error("角色数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/roleEdit";
    }

    @RequestMapping(path = {"/role/delete/{roleId}"})
    public String delete(@PathVariable("roleId") int roleId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            msg = this.roleService.delete(roleId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("角色删除出错");
            msg = "角色删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/role";
    }

}