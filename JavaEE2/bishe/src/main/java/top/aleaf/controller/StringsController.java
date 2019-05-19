package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
import top.aleaf.model.Strings;
import top.aleaf.model.ViewObject;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.service.StringsService;

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
public class StringsController {
    public static final Logger LOGGER = LoggerFactory.getLogger(StringsController.class);
    @Resource
    private StringsService stringsService;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/strings"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Strings strings, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            List<Strings> stringsList = this.stringsService.getAll(strings);
            List<ViewObject> vos = Lists.newArrayList();
            for (Strings item : stringsList) {
                ViewObject vo = new ViewObject();
                vo.set("strings", item);

                vo.set("entityType", EntityType.valueMap.get(item.getEntityType()).getDesc());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());
            List<EntityType> entityTypeList = Lists.newArrayList(EntityType.valueMap.values());
            model.addAttribute("entityTypeList", entityTypeList);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(stringsList));
            model.addAttribute("strings", strings);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("常用字符串列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/strings";
    }

    @RequestMapping(path = {"/strings/edit"}, method = RequestMethod.POST)
    public String editStrings(Strings strings, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            boolean isSave = strings.getId() == null;
            msg = this.stringsService.save(strings) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("常用字符串编辑出错");
            msg = "常用字符串编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/strings";
    }

    @RequestMapping(path = {"/strings/forEdit"})
    public String forUpdate(@RequestParam(value = "stringsId", required = false) Integer stringsId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            if (stringsId != null) {
                Strings strings = this.stringsService.getByPrimaryKey(stringsId);
                model.addAttribute("strings", strings);
            }
            List<EntityType> entityTypeList = Lists.newArrayList(EntityType.valueMap.values());
            model.addAttribute("entityTypeList", entityTypeList);
        } catch (Exception e) {
            LOGGER.error("常用字符串数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/stringsEdit";
    }

    @RequestMapping(path = {"/strings/delete/{stringsId}"})
    public String delete(@PathVariable("stringsId") int stringsId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            msg = this.stringsService.delete(stringsId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("常用字符串删除出错");
            msg = "常用字符串删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/strings";
    }
}