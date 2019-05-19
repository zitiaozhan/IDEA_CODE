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
import top.aleaf.service.PrizeService;
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
public class PrizeController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PrizeController.class);
    @Resource
    private PrizeService prizeService;
    @Resource
    private UserService userService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/prize"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          Prize prize, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Prize> prizeList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                prize.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                prize.setId(id);
            }
            prizeList = this.prizeService.getAll(prize);
            List<ViewObject> vos = Lists.newArrayList();
            for (Prize item : prizeList) {
                ViewObject vo = new ViewObject();
                vo.set("prize", item);
                vo.set("author", userService.getByNumber(item.getAuthor()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(prizeList));
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
        String msg;
        try {
            boolean isSave = prize.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = (isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                if (isSave && localUser != null) {
                    prize.setCreatedId(localUser.getId());
                    prize.setCreatedDate(new Date());
                }
                boolean result = this.prizeService.save(prize);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    Prize entity = this.prizeService.getByPrimaryKey(prize.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(prize.getId(), EntityType.PRIZE, null, status, EventType.REEDIT);
                    }
                }
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
                model.addAttribute("authorName", this.userService.getByNumber(prize.getAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("获奖列表数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/prizeEdit";
    }

    @RequestMapping(path = {"/prize/delete/{prizeId}"})
    public String delete(@PathVariable("prizeId") int prizeId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.prizeService.getByPrimaryKey(prizeId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                Prize entity = this.prizeService.getByPrimaryKey(prizeId);
                boolean result = this.prizeService.delete(prizeId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(prizeId, EntityType.PRIZE, null, status, EventType.DELETE);
                    }
                }
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
                boolean result = this.prizeService.setStatus(prizeId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/prize";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(prizeId, EntityType.PRIZE, option, status, EventType.APPROVE);
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