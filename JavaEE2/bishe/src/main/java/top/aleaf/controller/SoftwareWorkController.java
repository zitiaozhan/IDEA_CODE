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
import top.aleaf.service.SoftwareWorkService;
import top.aleaf.sync.EventType;
import top.aleaf.utils.ConstantUtil;

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
public class SoftwareWorkController {
    public static final Logger LOGGER = LoggerFactory.getLogger(SoftwareWorkController.class);
    @Resource
    private SoftwareWorkService softwareWorkService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/softwareWork"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          SoftwareWork softwareWork, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<SoftwareWork> softwareWorkList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                softwareWork.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                softwareWork.setId(id);
            }
            softwareWorkList = this.softwareWorkService.getAll(softwareWork);
            List<ViewObject> vos = Lists.newArrayList();
            for (SoftwareWork item : softwareWorkList) {
                ViewObject vo = new ViewObject();
                vo.set("softwareWork", item);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(softwareWorkList));
            model.addAttribute("softwareWork", softwareWork);
            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("软件著作列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/softwareWork";
    }

    @RequestMapping(path = {"/softwareWork/edit"}, method = RequestMethod.POST)
    public String editSoftwareWork(SoftwareWork softwareWork, Model model) {
        String msg;
        try {
            boolean isSave = softwareWork.getId() == null;
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
                    softwareWork.setCreatedId(localUser.getId());
                    softwareWork.setCreatedDate(new Date());
                }
                boolean result = this.softwareWorkService.save(softwareWork);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    SoftwareWork entity = this.softwareWorkService.getByPrimaryKey(softwareWork.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(softwareWork.getId(), EntityType.SOFTWARE_WORK, null, status, EventType.REEDIT);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("软件著作编辑出错");
            msg = "软件著作编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/softwareWork";
    }

    @RequestMapping(path = {"/softwareWork/forEdit"})
    public String forUpdate(@RequestParam(value = "softwareWorkId", required = false) Integer softwareWorkId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            if (softwareWorkId != null) {
                SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(softwareWorkId);
                model.addAttribute("softwareWork", softwareWork);
            }
        } catch (Exception e) {
            LOGGER.error("软件著作数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/softwareWorkEdit";
    }

    @RequestMapping(path = {"/softwareWork/delete/{softwareWorkId}"})
    public String delete(@PathVariable("softwareWorkId") int softwareWorkId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.softwareWorkService.getByPrimaryKey(softwareWorkId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                SoftwareWork entity = this.softwareWorkService.getByPrimaryKey(softwareWorkId);
                boolean result = this.softwareWorkService.delete(softwareWorkId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(softwareWorkId, EntityType.SOFTWARE_WORK, null, status, EventType.DELETE);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("软件著作删除出错");
            msg = "软件著作删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/softwareWork";
    }

    @RequestMapping(path = {"/softwareWork/approve/{softwareWorkId}"})
    public String approve(@PathVariable("softwareWorkId") int softwareWorkId,
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
                boolean result = this.softwareWorkService.setStatus(softwareWorkId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/softwareWork";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(softwareWorkId, EntityType.SOFTWARE_WORK, option, status, EventType.APPROVE);
            }
        } catch (Exception e) {
            LOGGER.error("软件著作审批出错");
            msg = "软件著作审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/softwareWork";
    }

}