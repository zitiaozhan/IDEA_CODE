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
import top.aleaf.service.SoftwareWorkService;
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
public class SoftwareWorkController {
    public static final Logger LOGGER = LoggerFactory.getLogger(SoftwareWorkController.class);
    @Autowired
    private SoftwareWorkService softwareWorkService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/softwareWork"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          SoftwareWork softwareWork, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<SoftwareWork> softwareWorkList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                softwareWork.setCreatedId(localUser.getId());
            }
            softwareWorkList = this.softwareWorkService.getAll(softwareWork);
            List<ViewObject> vos = new ArrayList<>();
            for (SoftwareWork item : softwareWorkList) {
                ViewObject vo = new ViewObject();
                vo.set("softwareWork", item);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<SoftwareWork>(softwareWorkList));
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
        String msg = null;
        try {
            boolean isSave = softwareWork.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    softwareWork.setCreatedId(localUser.getId());
                    softwareWork.setCreatedDate(new Date());
                }

                msg = this.softwareWorkService.save(softwareWork) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
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
            /*List<Strings> stringsList = this.stringsService.getAll(
                    new Strings().setEntityType(1).setEntityField(""));
            model.addAttribute("stringsList", stringsList);*/
        } catch (Exception e) {
            LOGGER.error("软件著作数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/softwareWorkEdit";
    }

    @RequestMapping(path = {"/softwareWork/delete/{softwareWorkId}"})
    public String delete(@PathVariable("softwareWorkId") int softwareWorkId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.softwareWorkService.getByPrimaryKey(softwareWorkId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.softwareWorkService.delete(softwareWorkId) ? "删除成功" : "删除失败";
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
                boolean result = this.softwareWorkService.setStatus(softwareWorkId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                SoftwareWork softwareWork = this.softwareWorkService.getByPrimaryKey(softwareWorkId);
                String projectName = softwareWork.getName();
                String projectUrl = "/softwareWork?softwareWorkId=" + softwareWorkId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(softwareWorkId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(6));
                eventModel.setEventOwnerId(softwareWork.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
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