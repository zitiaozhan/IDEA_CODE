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
import top.aleaf.service.LectureService;
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
public class LectureController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LectureController.class);
    @Autowired
    private LectureService lectureService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/lecture"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Lecture lecture, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Lecture> lectureList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                lecture.setCreatedId(localUser.getId());
            }
            lectureList = this.lectureService.getAll(lecture);

            List<ViewObject> vos = new ArrayList<>();
            for (Lecture item : lectureList) {
                ViewObject vo = new ViewObject();
                vo.set("lecture", item);
                vo.set("rapporteur", userService.getByPrimaryKey(item.getRapporteur()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Lecture>(lectureList));
            model.addAttribute("lecture", lecture);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/lecture";
    }

    @RequestMapping(path = {"/lecture/edit"}, method = RequestMethod.POST)
    public String editLecture(Lecture lecture, Model model) {
        String msg = null;
        try {
            boolean isSave = lecture.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    lecture.setCreatedId(localUser.getId());
                    lecture.setCreatedDate(new Date());
                }

                msg = this.lectureService.save(lecture) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座编辑出错");
            msg = "学术讲座编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/lecture";
    }

    @RequestMapping(path = {"/lecture/forEdit"})
    public String forUpdate(@RequestParam(value = "lectureId", required = false) Integer lectureId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (lectureId != null) {
                Lecture lecture = this.lectureService.getByPrimaryKey(lectureId);
                model.addAttribute("lecture", lecture);
                model.addAttribute("rapporteurName", this.userService.getByPrimaryKey(lecture.getRapporteur()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/lectureEdit";
    }

    @RequestMapping(path = {"/lecture/delete/{lectureId}"})
    public String delete(@PathVariable("lectureId") int lectureId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.lectureService.getByPrimaryKey(lectureId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.lectureService.delete(lectureId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座删除出错");
            msg = "学术讲座删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/lecture";
    }

    @RequestMapping(path = {"/lecture/approve/{lectureId}"})
    public String approve(@PathVariable("lectureId") int lectureId,
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
                boolean result = this.lectureService.setStatus(lectureId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                Lecture lecture = this.lectureService.getByPrimaryKey(lectureId);
                String projectName = lecture.getName();
                String projectUrl = "/lecture?lectureId=" + lectureId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(lectureId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(7));
                eventModel.setEventOwnerId(lecture.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座审批出错");
            msg = "学术讲座审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/lecture";
    }

}