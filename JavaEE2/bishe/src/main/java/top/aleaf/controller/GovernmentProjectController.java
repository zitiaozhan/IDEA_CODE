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
import top.aleaf.service.GovernmentProjectService;
import top.aleaf.service.UserService;
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
public class GovernmentProjectController {
    public static final Logger LOGGER = LoggerFactory.getLogger(GovernmentProjectController.class);
    @Resource
    private GovernmentProjectService governmentProjectService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private BaseController baseController;

    @RequestMapping(path = {"/governmentProject"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          GovernmentProject governmentProject, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<GovernmentProject> governmentProjectList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                governmentProject.setCreatedId(localUser.getId());
            }
            if (id>0){
                governmentProject.setId(id);
            }
            governmentProjectList = this.governmentProjectService.getAll(governmentProject);

            List<ViewObject> vos = Lists.newArrayList();
            for (GovernmentProject item : governmentProjectList) {
                ViewObject vo = new ViewObject();
                vo.set("governmentProject", item);
                vo.set("chargePerson", userService.getByNumber(item.getChargePerson()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(governmentProjectList));
            model.addAttribute("governmentProject", governmentProject);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("纵向项目列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/governmentProject";
    }

    @RequestMapping(path = {"/governmentProject/edit"}, method = RequestMethod.POST)
    public String editGovernmentProject(GovernmentProject governmentProject, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean isSave = governmentProject.getId() == null;
            if ((isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                governmentProject.setChargePerson(localUser.getNumber());
                String projectDate = governmentProject.getProjectDate();
                if (null != governmentProject.getHelpContent() && !"".equals(governmentProject.getHelpContent().trim())) {
                    projectDate += "X" + governmentProject.getHelpContent();
                }
                governmentProject.setProjectDate(projectDate);

                if (isSave) {
                    governmentProject.setCreatedId(localUser.getId());
                    governmentProject.setCreatedDate(new Date());
                }
                boolean result = this.governmentProjectService.save(governmentProject);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    GovernmentProject entity = this.governmentProjectService.getByPrimaryKey(governmentProject.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(governmentProject.getId(), EntityType.GOVERNMENT_PROJECT, null, status, EventType.REEDIT);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("纵向项目编辑出错");
            msg = "纵向项目编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/governmentProject";
    }

    @RequestMapping(path = {"/governmentProject/forEdit"})
    public String forUpdate(@RequestParam(value = "governmentProjectId", required = false) Integer governmentProjectId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (governmentProjectId != null) {
                GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(governmentProjectId);
                String projectDate = governmentProject.getProjectDate();
                if (projectDate.contains(ConstantUtil.DATE_SPLIT)) {
                    String[] xx = projectDate.split(ConstantUtil.DATE_SPLIT);
                    governmentProject.setProjectDate(xx[0]);
                    governmentProject.setHelpContent(xx[1]);
                }
                model.addAttribute("chargePersonName", userService.getByNumber(governmentProject.getChargePerson()).getName());
                model.addAttribute("governmentProject", governmentProject);
            }
        } catch (Exception e) {
            LOGGER.error("纵向项目数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/governmentProjectEdit";
    }

    @RequestMapping(path = {"/governmentProject/delete/{governmentProjectId}"})
    public String delete(@PathVariable("governmentProjectId") int governmentProjectId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.governmentProjectService.getByPrimaryKey(governmentProjectId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                GovernmentProject entity = this.governmentProjectService.getByPrimaryKey(governmentProjectId);
                boolean result = this.governmentProjectService.delete(governmentProjectId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(governmentProjectId, EntityType.GOVERNMENT_PROJECT, null, status, EventType.DELETE);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("纵向项目删除出错");
            msg = "纵向项目删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/governmentProject";
    }

    @RequestMapping(path = {"/governmentProject/approve/{governmentProjectId}"})
    public String approve(@PathVariable("governmentProjectId") int governmentProjectId,
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
                boolean result = this.governmentProjectService.setStatus(governmentProjectId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/governmentProject";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(governmentProjectId, EntityType.GOVERNMENT_PROJECT, option, status, EventType.APPROVE);
            }
        } catch (Exception e) {
            LOGGER.error("纵向项目审批出错");
            msg = "纵向项目审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/governmentProject";
    }

}