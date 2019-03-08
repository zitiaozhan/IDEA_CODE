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
import top.aleaf.service.TeachingMaterialService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventProducer;
import top.aleaf.sync.EventType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class TeachingMaterialController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TeachingMaterialController.class);
    @Autowired
    private TeachingMaterialService teachingMaterialService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/teachingMaterial"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          TeachingMaterial teachingMaterial, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<TeachingMaterial> teachingMaterialList = new ArrayList<>();
            if (localRole != null) {
                if ("教师".equals(localRole.getName())) {
                    teachingMaterial.setCreatedId(localUser.getId());
                }
                teachingMaterialList = this.teachingMaterialService.getAll(teachingMaterial);
            }
            List<ViewObject> vos = new ArrayList<>();
            for (TeachingMaterial item : teachingMaterialList) {
                ViewObject vo = new ViewObject();
                vo.set("teachingMaterial", item);
                vo.set("author", userService.getByPrimaryKey(item.getAuthor()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<TeachingMaterial>(teachingMaterialList));
            model.addAttribute("teachingMaterial", teachingMaterial);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("教材列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/teachingMaterial";
    }

    @RequestMapping(path = {"/teachingMaterial/edit"}, method = RequestMethod.POST)
    public String editTeachingMaterial(TeachingMaterial teachingMaterial, Model model) {
        String msg = null;
        try {
            boolean isSave = teachingMaterial.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    teachingMaterial.setCreatedId(localUser.getId());
                    teachingMaterial.setCreatedDate(new Date());
                }

                msg = this.teachingMaterialService.save(teachingMaterial) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("教材编辑出错");
            msg = "教材编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/teachingMaterial";
    }

    @RequestMapping(path = {"/teachingMaterial/forEdit"})
    public String forUpdate(@RequestParam(value = "teachingMaterialId", required = false) Integer teachingMaterialId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            if (teachingMaterialId != null) {
                TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(teachingMaterialId);
                model.addAttribute("teachingMaterial", teachingMaterial);
                model.addAttribute("authorName", this.userService.getByPrimaryKey(teachingMaterial.getAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("教材数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/teachingMaterialEdit";
    }

    @RequestMapping(path = {"/teachingMaterial/delete/{teachingMaterialId}"})
    public String delete(@PathVariable("teachingMaterialId") int teachingMaterialId,
                         Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.teachingMaterialService.getByPrimaryKey(teachingMaterialId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.teachingMaterialService.delete(teachingMaterialId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("教材删除出错");
            msg = "教材删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/teachingMaterial";
    }

    @RequestMapping(path = {"/teachingMaterial/approve/{teachingMaterialId}"})
    public String approve(@PathVariable("teachingMaterialId") int teachingMaterialId,
                          @RequestParam("status") int status, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("teacher".equals(localRole.getDetail())) {
                msg = "受限制的操作";
            } else {
                boolean result = this.teachingMaterialService.setStatus(teachingMaterialId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                TeachingMaterial teachingMaterial = this.teachingMaterialService.getByPrimaryKey(teachingMaterialId);
                String projectName = teachingMaterial.getName();
                String projectUrl = "/teachingMaterial?teachingMaterialId=" + teachingMaterialId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(teachingMaterialId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(4));
                eventModel.setEventOwnerId(teachingMaterial.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("教材审批出错");
            msg = "教材审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/teachingMaterial";
    }

}