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
import top.aleaf.service.CompanyProjectService;
import top.aleaf.service.UserService;
import top.aleaf.sync.EventType;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class CompanyProjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyProjectController.class);

    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private BaseController baseController;

    @RequestMapping(path = {"/companyProject"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          CompanyProject companyProject, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            List<CompanyProject> companyProjectList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                companyProject.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                companyProject.setId(id);
            }
            companyProjectList = this.companyProjectService.getAll(companyProject);

            List<ViewObject> vos = Lists.newArrayList();
            for (CompanyProject item : companyProjectList) {
                ViewObject vo = new ViewObject();
                vo.set("companyProject", item);
                vo.set("chargePerson", userService.getByNumber(item.getChargePerson()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());
            model.addAttribute("companyProject", companyProject);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(companyProjectList));

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("横向项目列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/companyProject";
    }

    @RequestMapping(path = {"/companyProject/edit"}, method = RequestMethod.POST)
    public String editCompanyProject(CompanyProject companyProject, Model model) {
        String msg;
        try {
            boolean isSave = companyProject.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                companyProject.setChargePerson(localUser.getNumber());
                String projectDate = companyProject.getProjectDate();
                if (null != companyProject.getHelpContent() && !"".equals(companyProject.getHelpContent().trim())) {
                    projectDate += "X" + companyProject.getHelpContent();
                }
                companyProject.setProjectDate(projectDate);

                if (isSave) {
                    companyProject.setCreatedId(localUser.getId());
                    companyProject.setCreatedDate(new Date());
                }

                boolean result = this.companyProjectService.save(companyProject);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    CompanyProject entity = this.companyProjectService.getByPrimaryKey(companyProject.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(companyProject.getId(), EntityType.COMPANY_PROJECT, null, status, EventType.REEDIT);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("横向项目编辑出错");
            msg = "横向项目编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/companyProject";
    }

    @RequestMapping(path = {"/companyProject/forEdit"})
    public String forUpdate(@RequestParam(value = "companyProjectId", required = false) Integer companyProjectId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (companyProjectId != null) {
                CompanyProject companyProject = this.companyProjectService.getByPrimaryKey(companyProjectId);
                String projectDate = companyProject.getProjectDate();
                if (projectDate.contains(ConstantUtil.DATE_SPLIT)) {
                    String[] xx = projectDate.split(ConstantUtil.DATE_SPLIT);
                    companyProject.setProjectDate(xx[0]);
                    companyProject.setHelpContent(xx[1]);
                }
                model.addAttribute("chargePersonName", userService.getByNumber(companyProject.getChargePerson()).getName());
                model.addAttribute("companyProject", companyProject);
            }
        } catch (Exception e) {
            LOGGER.error("横向项目数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/companyProjectEdit";
    }

    @RequestMapping(path = {"/companyProject/delete/{companyProjectId}"})
    public String delete(@PathVariable("companyProjectId") int companyProjectId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.companyProjectService.getByPrimaryKey(companyProjectId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                CompanyProject entity = this.companyProjectService.getByPrimaryKey(companyProjectId);
                boolean result = this.companyProjectService.delete(companyProjectId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(companyProjectId, EntityType.COMPANY_PROJECT, null, status, EventType.DELETE);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("横向项目删除出错");
            msg = "横向项目删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/companyProject";
    }

    @RequestMapping(path = {"/companyProject/approve/{companyProjectId}"})
    public String approve(@PathVariable("companyProjectId") int companyProjectId,
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
                boolean result = this.companyProjectService.setStatus(companyProjectId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/companyProject";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(companyProjectId, EntityType.COMPANY_PROJECT, option, status, EventType.APPROVE);
            }
        } catch (Exception e) {
            LOGGER.error("横向项目审批出错");
            msg = "横向项目审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/companyProject";
    }

}