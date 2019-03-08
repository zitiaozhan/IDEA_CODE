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
import top.aleaf.service.AcademicExchangeService;
import top.aleaf.service.InfoTypeService;
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
public class AcademicExchangeController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AcademicExchangeController.class);
    @Autowired
    private AcademicExchangeService academicExchangeService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/academicExchange"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          AcademicExchange academicExchange, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<AcademicExchange> academicExchangeList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                academicExchange.setCreatedId(localUser.getId());
            }
            academicExchangeList = this.academicExchangeService.getAll(academicExchange);

            List<ViewObject> vos = new ArrayList<>();
            for (AcademicExchange item : academicExchangeList) {
                ViewObject vo = new ViewObject();
                vo.set("academicExchange", item);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<AcademicExchange>(academicExchangeList));
            model.addAttribute("academicExchange", academicExchange);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("学术交流列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/academicExchange";
    }

    @RequestMapping(path = {"/academicExchange/edit"}, method = RequestMethod.POST)
    public String editAcademicExchange(AcademicExchange academicExchange, Model model) {
        String msg = null;
        try {
            boolean isSave = academicExchange.getId() == null;
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((!isSave && "teacher".equals(localRole.getDetail())) ||
                    (isSave && "manager".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    academicExchange.setCreatedId(localUser.getId());
                    academicExchange.setCreatedDate(new Date());
                }
                msg = this.academicExchangeService.save(academicExchange) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("学术交流编辑出错");
            msg = "学术交流编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/academicExchange";
    }

    @RequestMapping(path = {"/academicExchange/forEdit"})
    public String forUpdate(@RequestParam(value = "academicExchangeId", required = false) Integer academicExchangeId,
                            Model model) {
        try {
            if (academicExchangeId != null) {
                AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(academicExchangeId);
                model.addAttribute("academicExchange", academicExchange);
            }
        } catch (Exception e) {
            LOGGER.error("学术交流数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/academicExchangeEdit";
    }

    @RequestMapping(path = {"/academicExchange/delete/{academicExchangeId}"})
    public String delete(@PathVariable("academicExchangeId") int academicExchangeId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.academicExchangeService.getByPrimaryKey(academicExchangeId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.academicExchangeService.delete(academicExchangeId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("学术交流删除出错");
            msg = "学术交流删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/academicExchange";
    }

    @RequestMapping(path = {"/academicExchange/approve/{academicExchangeId}"})
    public String approve(@PathVariable("academicExchangeId") int academicExchangeId,
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
                boolean result = this.academicExchangeService.setStatus(academicExchangeId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                AcademicExchange academicExchange = this.academicExchangeService.getByPrimaryKey(academicExchangeId);
                String projectName = academicExchange.getMeetingName();
                String projectUrl = "/academicExchange?academicExchangeId=" + academicExchangeId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(academicExchangeId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(8));
                eventModel.setEventOwnerId(academicExchange.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("学术交流审批出错");
            msg = "学术交流审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/academicExchange";
    }

}