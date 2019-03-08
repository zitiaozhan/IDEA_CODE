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
import top.aleaf.model.*;
import top.aleaf.service.InfoTypeService;
import top.aleaf.service.StringsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class StringsController {
    public static final Logger LOGGER = LoggerFactory.getLogger(StringsController.class);
    @Autowired
    private StringsService stringsService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/strings"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Strings strings, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole==null||!"smanager".equals(localRole.getDetail())){
                return "redirect:/index";
            }

            List<Strings> stringsList = this.stringsService.getAll(strings);
            List<ViewObject> vos = new ArrayList<>();
            for (Strings item : stringsList) {
                ViewObject vo = new ViewObject();
                vo.set("strings", item);

                vo.set("entityType", infoTypeService.getByPrimaryKey(item.getEntityType()).getEntityName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());
            List<InfoType> infoTypeList=this.infoTypeService.getAll();
            model.addAttribute("infoTypeList", infoTypeList);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Strings>(stringsList));
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
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole==null||!"smanager".equals(localRole.getDetail())){
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
            if (localRole==null||!"smanager".equals(localRole.getDetail())){
                return "redirect:/index";
            }

            if (stringsId != null) {
                Strings strings = this.stringsService.getByPrimaryKey(stringsId);
                model.addAttribute("strings", strings);
            }
            List<InfoType> infoTypeList=this.infoTypeService.getAll();
            model.addAttribute("infoTypeList", infoTypeList);
        } catch (Exception e) {
            LOGGER.error("常用字符串数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/stringsEdit";
    }

    @RequestMapping(path = {"/strings/delete/{stringsId}"})
    public String delete(@PathVariable("stringsId") int stringsId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole==null||!"smanager".equals(localRole.getDetail())){
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