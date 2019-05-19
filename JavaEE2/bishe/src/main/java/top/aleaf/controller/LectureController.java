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
import top.aleaf.service.LectureService;
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
public class LectureController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LectureController.class);
    @Resource
    private LectureService lectureService;
    @Resource
    private UserService userService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/lecture"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          Lecture lecture, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Lecture> lectureList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                lecture.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                lecture.setId(id);
            }
            lectureList = this.lectureService.getAll(lecture);

            List<ViewObject> vos = Lists.newArrayList();
            for (Lecture item : lectureList) {
                ViewObject vo = new ViewObject();
                vo.set("lecture", item);
                vo.set("rapporteur", userService.getByNumber(item.getRapporteur()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(lectureList));
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
        String msg;
        try {
            boolean isSave = lecture.getId() == null;
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
                    lecture.setCreatedId(localUser.getId());
                    lecture.setCreatedDate(new Date());
                }
                boolean result = this.lectureService.save(lecture);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    Lecture entity = this.lectureService.getByPrimaryKey(lecture.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(lecture.getId(), EntityType.LECTURE, null, status, EventType.REEDIT);
                    }
                }
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
                model.addAttribute("rapporteurName", this.userService.getByNumber(lecture.getRapporteur()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("学术讲座数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/lectureEdit";
    }

    @RequestMapping(path = {"/lecture/delete/{lectureId}"})
    public String delete(@PathVariable("lectureId") int lectureId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.lectureService.getByPrimaryKey(lectureId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                Lecture entity = this.lectureService.getByPrimaryKey(lectureId);
                boolean result = this.lectureService.delete(lectureId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(lectureId, EntityType.LECTURE, null, status, EventType.DELETE);
                    }
                }
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
                boolean result = this.lectureService.setStatus(lectureId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/lecture";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(lectureId, EntityType.LECTURE, option, status, EventType.APPROVE);
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