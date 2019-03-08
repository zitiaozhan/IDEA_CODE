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
import top.aleaf.service.PaperService;
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
public class PaperController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PaperController.class);
    @Autowired
    private PaperService paperService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/paper"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Paper paper, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Paper> paperList = new ArrayList<>();
            if (localRole != null) {
                if ("教师".equals(localRole.getName())) {
                    paper.setCreatedId(localUser.getId());
                }
                paperList = this.paperService.getAll(paper);
            }

            List<ViewObject> vos = new ArrayList<>();
            for (Paper item : paperList) {
                ViewObject vo = new ViewObject();
                vo.set("paper", item);
                vo.set("firstAuthor", userService.getByPrimaryKey(item.getFirstAuthor()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Paper>(paperList));
            model.addAttribute("paper", paper);
            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("论文列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/paper";
    }

    @RequestMapping(path = {"/paper/edit"}, method = RequestMethod.POST)
    public String editPaper(Paper paper, Model model) {
        String msg = null;
        try {
            boolean isSave = paper.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || !isSave && "teacher".equals(localRole.getDetail())) {
                msg = "受限制的操作";
            } else {
                paper.setFirstAuthor(localUser.getId());
                if (isSave && localUser != null) {
                    paper.setCreatedId(localUser.getId());
                    paper.setCreatedDate(new Date());
                }

                msg = this.paperService.save(paper) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("论文编辑出错");
            msg = "论文编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/paper";
    }

    @RequestMapping(path = {"/paper/forEdit"})
    public String forUpdate(@RequestParam(value = "paperId", required = false) Integer paperId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (paperId != null) {
                Paper paper = this.paperService.getByPrimaryKey(paperId);
                model.addAttribute("paper", paper);
                model.addAttribute("firstAuthorName", this.userService.getByPrimaryKey(paper.getFirstAuthor()).getName());
            }
            /*List<Strings> stringsList = this.stringsService.getAll(
                    new Strings().setEntityType(1).setEntityField(""));
            model.addAttribute("stringsList", stringsList);*/
        } catch (Exception e) {
            LOGGER.error("论文数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/paperEdit";
    }

    @RequestMapping(path = {"/paper/delete/{paperId}"})
    public String delete(@PathVariable("paperId") int paperId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.paperService.getByPrimaryKey(paperId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.paperService.delete(paperId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("论文删除出错");
            msg = "论文删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/paper";
    }

    @RequestMapping(path = {"/paper/approve/{paperId}"})
    public String approve(@PathVariable("paperId") int paperId,
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
                boolean result = this.paperService.setStatus(paperId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                Paper paper = this.paperService.getByPrimaryKey(paperId);
                String projectName = paper.getName();
                String projectUrl = "/paper?paperId=" + paperId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(paperId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(9));
                eventModel.setEventOwnerId(paper.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            msg = "论文审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/paper";
    }

}