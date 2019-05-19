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
import top.aleaf.model.*;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.service.TeachingMaterialService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventType;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 郭新晔
 */
@Controller
public class TeachingMaterialController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TeachingMaterialController.class);
    @Resource
    private TeachingMaterialService teachingMaterialService;
    @Resource
    private UserService userService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/teachingMaterial"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          TeachingMaterial teachingMaterial, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<TeachingMaterial> teachingMaterialList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                teachingMaterial.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                teachingMaterial.setId(id);
            }

            teachingMaterialList = this.teachingMaterialService.getAll(teachingMaterial);
            List<ViewObject> vos = Lists.newArrayList();
            for (TeachingMaterial item : teachingMaterialList) {
                ViewObject vo = new ViewObject();
                vo.set("teachingMaterial", item);
                vo.set("author", userService.getByNumber(item.getAuthor()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(teachingMaterialList));
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
        String msg;
        try {
            boolean isSave = teachingMaterial.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = (isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    teachingMaterial.setCreatedId(localUser.getId());
                    teachingMaterial.setCreatedDate(new Date());
                }

                boolean result = this.teachingMaterialService.save(teachingMaterial);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    TeachingMaterial entity = this.teachingMaterialService.getByPrimaryKey(teachingMaterial.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(teachingMaterial.getId(), EntityType.TEACHING_MATERIAL, null, status, EventType.REEDIT);
                    }
                }
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
                model.addAttribute("authorName", this.userService.getByNumber(teachingMaterial.getAuthor()).getName());
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
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.teachingMaterialService.getByPrimaryKey(teachingMaterialId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                TeachingMaterial entity = this.teachingMaterialService.getByPrimaryKey(teachingMaterialId);
                boolean result = this.teachingMaterialService.delete(teachingMaterialId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(teachingMaterialId, EntityType.TEACHING_MATERIAL, null, status, EventType.DELETE);
                    }
                }
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
                          @RequestParam("status") int status, Model model,
                          @RequestParam(value = "option", required = false) String option) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail())) {
                msg = "受限制的操作";
            } else {
                boolean result = this.teachingMaterialService.setStatus(teachingMaterialId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/teachingMaterial";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(teachingMaterialId, EntityType.TEACHING_MATERIAL, option, status, EventType.APPROVE);
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