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
import top.aleaf.service.PatentService;
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
public class PatentController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PatentController.class);
    @Resource
    private PatentService patentService;
    @Resource
    private UserService userService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/patent"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          Patent patent, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Patent> patentList = Lists.newArrayList();
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                patent.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                patent.setId(id);
            }
            patentList = this.patentService.getAll(patent);
            List<ViewObject> vos = Lists.newArrayList();
            for (Patent item : patentList) {
                ViewObject vo = new ViewObject();
                vo.set("patent", item);
                vo.set("author", userService.getByNumber(item.getAuthor()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(patentList));
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
        String msg;
        try {
            boolean isSave = patent.getId() == null;
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
                    patent.setCreatedId(localUser.getId());
                    patent.setCreatedDate(new Date());
                }
                boolean result = this.patentService.save(patent);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    Patent entity = this.patentService.getByPrimaryKey(patent.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(patent.getId(), EntityType.PATENT, null, status, EventType.REEDIT);
                    }
                }
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
                model.addAttribute("authorName", this.userService.getByNumber(patent.getAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("专利数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/patentEdit";
    }

    @RequestMapping(path = {"/patent/delete/{patentId}"})
    public String delete(@PathVariable("patentId") int patentId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.patentService.getByPrimaryKey(patentId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                Patent entity = this.patentService.getByPrimaryKey(patentId);
                boolean result = this.patentService.delete(patentId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(patentId, EntityType.PATENT, null, status, EventType.DELETE);
                    }
                }
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
                boolean result = this.patentService.setStatus(patentId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/patent";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(patentId, EntityType.PATENT, option, status, EventType.APPROVE);
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