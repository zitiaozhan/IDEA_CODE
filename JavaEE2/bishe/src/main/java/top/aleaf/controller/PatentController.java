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
import top.aleaf.service.PatentService;
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
public class PatentController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PatentController.class);
    @Autowired
    private PatentService patentService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/patent"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Patent patent, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Patent> patentList = new ArrayList<>();
            if (localRole != null) {
                if ("教师".equals(localRole.getName())) {
                    patent.setCreatedId(localUser.getId());
                }
                patentList = this.patentService.getAll(patent);
            }
            List<ViewObject> vos = new ArrayList<>();
            for (Patent item : patentList) {
                ViewObject vo = new ViewObject();
                vo.set("patent", item);
                vo.set("author", userService.getByPrimaryKey(item.getAuthor()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Patent>(patentList));
            model.addAttribute("patent", patent);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("专利列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/patent";
    }

    @RequestMapping(path = {"/patent/edit"}, method = RequestMethod.POST)
    public String editPatent(Patent patent, Model model) {
        String msg = null;
        try {
            boolean isSave = patent.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave && localUser != null) {
                    patent.setCreatedId(localUser.getId());
                    patent.setCreatedDate(new Date());
                }

                msg = this.patentService.save(patent) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("专利编辑出错");
            msg = "专利编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/patent";
    }

    @RequestMapping(path = {"/patent/forEdit"})
    public String forUpdate(@RequestParam(value = "patentId", required = false) Integer patentId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (patentId != null) {
                Patent patent = this.patentService.getByPrimaryKey(patentId);
                model.addAttribute("patent", patent);
                model.addAttribute("authorName", this.userService.getByPrimaryKey(patent.getAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("专利数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/patentEdit";
    }

    @RequestMapping(path = {"/patent/delete/{patentId}"})
    public String delete(@PathVariable("patentId") int patentId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.patentService.getByPrimaryKey(patentId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.patentService.delete(patentId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("专利删除出错");
            msg = "专利删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/patent";
    }

    @RequestMapping(path = {"/patent/approve/{patentId}"})
    public String approve(@PathVariable("patentId") int patentId,
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
                boolean result = this.patentService.setStatus(patentId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                Patent patent = this.patentService.getByPrimaryKey(patentId);
                String projectName = patent.getName();
                String projectUrl = "/patent?patentId=" + patentId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(patentId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(5));
                eventModel.setEventOwnerId(patent.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("专利审批出错");
            msg = "专利审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/patent";
    }

}