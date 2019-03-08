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
import top.aleaf.service.GovernmentProjectService;
import top.aleaf.service.InfoTypeService;
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
public class GovernmentProjectController {
    public static final Logger LOGGER = LoggerFactory.getLogger(GovernmentProjectController.class);
    @Autowired
    private GovernmentProjectService governmentProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/governmentProject"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          GovernmentProject governmentProject, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<GovernmentProject> governmentProjectList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                governmentProject.setCreatedId(localUser.getId());
            }
            governmentProjectList = this.governmentProjectService.getAll(governmentProject);

            List<ViewObject> vos = new ArrayList<>();
            for (GovernmentProject item : governmentProjectList) {
                ViewObject vo = new ViewObject();
                vo.set("governmentProject", item);
                vo.set("chargePerson", userService.getByPrimaryKey(item.getChargePerson()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<GovernmentProject>(governmentProjectList));
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
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean isSave = governmentProject.getId() == null;
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                governmentProject.setChargePerson(localUser.getId());
                governmentProject.setProjectDate(governmentProject.getProjectDate() + "X" + governmentProject.getHelpContent());

                if (isSave) {
                    governmentProject.setCreatedId(localUser.getId());
                    governmentProject.setCreatedDate(new Date());
                }

                msg = this.governmentProjectService.save(governmentProject) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
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
                if (projectDate.contains("X")) {
                    String[] xx = projectDate.split("X");
                    governmentProject.setProjectDate(xx[0]);
                    governmentProject.setHelpContent(xx[1]);
                }
                model.addAttribute("chargePersonName", userService.getByPrimaryKey(governmentProject.getChargePerson()).getName());
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
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.governmentProjectService.getByPrimaryKey(governmentProjectId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.governmentProjectService.delete(governmentProjectId) ? "删除成功" : "删除失败";
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
                boolean result = this.governmentProjectService.setStatus(governmentProjectId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                GovernmentProject governmentProject = this.governmentProjectService.getByPrimaryKey(governmentProjectId);
                String projectName = governmentProject.getName();
                String projectUrl = "/governmentProject?governmentProjectId=" + governmentProjectId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(governmentProjectId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(2));
                eventModel.setEventOwnerId(governmentProject.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
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