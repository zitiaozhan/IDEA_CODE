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
import top.aleaf.service.CompanyProjectService;
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
public class CompanyProjectController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CompanyProjectController.class);
    @Autowired
    private CompanyProjectService companyProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/companyProject"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          CompanyProject companyProject, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            List<CompanyProject> companyProjectList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                companyProject.setCreatedId(localUser.getId());
            }
            companyProjectList = this.companyProjectService.getAll(companyProject);

            List<ViewObject> vos = new ArrayList<>();
            for (CompanyProject item : companyProjectList) {
                ViewObject vo = new ViewObject();
                vo.set("companyProject", item);
                vo.set("chargePerson", userService.getByPrimaryKey(item.getChargePerson()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());
            model.addAttribute("companyProject", companyProject);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<CompanyProject>(companyProjectList));

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
        String msg = null;
        try {
            boolean isSave = companyProject.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                companyProject.setChargePerson(localUser.getId());
                companyProject.setProjectDate(companyProject.getProjectDate() + "X" + companyProject.getHelpContent());

                if (isSave) {
                    companyProject.setCreatedId(localUser.getId());
                    companyProject.setCreatedDate(new Date());
                }

                msg = this.companyProjectService.save(companyProject) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
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
                if (projectDate.contains("X")) {
                    String[] xx = projectDate.split("X");
                    companyProject.setProjectDate(xx[0]);
                    companyProject.setHelpContent(xx[1]);
                }
                model.addAttribute("chargePersonName", userService.getByPrimaryKey(companyProject.getChargePerson()).getName());
                model.addAttribute("companyProject", companyProject);
            }
            /*List<Strings> stringsList = this.stringsService.getAll(
                    new Strings().setEntityType(1).setEntityField(""));
            model.addAttribute("stringsList", stringsList);*/
        } catch (Exception e) {
            LOGGER.error("横向项目数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/companyProjectEdit";
    }

    @RequestMapping(path = {"/companyProject/delete/{companyProjectId}"})
    public String delete(@PathVariable("companyProjectId") int companyProjectId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.companyProjectService.getByPrimaryKey(companyProjectId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.companyProjectService.delete(companyProjectId) ? "删除成功" : "删除失败";
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
                boolean result = this.companyProjectService.setStatus(companyProjectId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                CompanyProject companyProject = this.companyProjectService.getByPrimaryKey(companyProjectId);
                String projectName = companyProject.getName();
                String projectUrl = "/companyProject?companyProjectId=" + companyProjectId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(companyProjectId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(1));
                eventModel.setEventOwnerId(companyProject.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("横向项目审批出错");
            msg = "横向项目审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/companyProject";
    }

    private void initData(Model model) {

    }
}