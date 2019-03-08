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
import top.aleaf.service.PrizeService;
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
public class PrizeController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PrizeController.class);
    @Autowired
    private PrizeService prizeService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/prize"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Prize prize, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Prize> prizeList = new ArrayList<>();
            if ("教师".equals(localRole.getName())) {
                prize.setCreatedId(localUser.getId());
            }
            prizeList = this.prizeService.getAll(prize);
            List<ViewObject> vos = new ArrayList<>();
            for (Prize item : prizeList) {
                ViewObject vo = new ViewObject();
                vo.set("prize", item);
                vo.set("author", userService.getByPrimaryKey(item.getAuthor()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Prize>(prizeList));
            model.addAttribute("prize", prize);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("获奖列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/prize";
    }

    @RequestMapping(path = {"/prize/edit"}, method = RequestMethod.POST)
    public String editPrize(Prize prize, Model model) {
        String msg = null;
        try {
            boolean isSave = prize.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ((isSave && "manager".equals(localRole.getDetail())) || (!isSave && "teacher".equals(localRole.getDetail()))) {
                msg = "受限制的操作";
            } else {
                if (isSave && localUser != null) {
                    prize.setCreatedId(localUser.getId());
                    prize.setCreatedDate(new Date());
                }

                msg = this.prizeService.save(prize) ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
            }
        } catch (Exception e) {
            LOGGER.error("获奖列表编辑出错");
            msg = "获奖列表编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/prize";
    }

    @RequestMapping(path = {"/prize/forEdit"})
    public String forUpdate(@RequestParam(value = "prizeId", required = false) Integer prizeId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (prizeId != null) {
                Prize prize = this.prizeService.getByPrimaryKey(prizeId);
                model.addAttribute("prize", prize);
                model.addAttribute("authorName", this.userService.getByPrimaryKey(prize.getAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("获奖列表数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/prizeEdit";
    }

    @RequestMapping(path = {"/prize/delete/{prizeId}"})
    public String delete(@PathVariable("prizeId") int prizeId, Model model) {
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            if ("manager".equals(localRole.getDetail()) || ("teacher".equals(localRole.getDetail()) && !localUser.getId().equals(this.prizeService.getByPrimaryKey(prizeId).getCreatedId()))) {
                msg = "受限制的操作";
            } else {
                msg = this.prizeService.delete(prizeId) ? "删除成功" : "删除失败";
            }
        } catch (Exception e) {
            LOGGER.error("获奖删除出错");
            msg = "获奖删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/prize";
    }

    @RequestMapping(path = {"/prize/approve/{prizeId}"})
    public String approve(@PathVariable("prizeId") int prizeId,
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
                boolean result = this.prizeService.setStatus(prizeId, status);
                msg = result ? "审批成功" : "审批失败";

                int resultStatus = result ? 1 : 2;
                Prize prize = this.prizeService.getByPrimaryKey(prizeId);
                String projectName = prize.getName();
                String projectUrl = "/prize?prizeId=" + prizeId;
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEntityId(prizeId);
                eventModel.setEntityType(this.infoTypeService.getByPrimaryKey(3));
                eventModel.setEventOwnerId(prize.getCreatedId());
                eventModel.setEventType(EventType.APPROVE);
                eventModel.addExt("resultStatus", resultStatus + "");
                eventModel.addExt("projectName", projectName);
                eventModel.addExt("projectUrl", projectUrl);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("获奖审批出错");
            msg = "获奖审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/prize";
    }

}