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
import top.aleaf.service.PaperService;
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
public class PaperController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PaperController.class);
    @Resource
    private PaperService paperService;
    @Resource
    private UserService userService;
    @Resource
    private BaseController baseController;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/paper"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          Paper paper, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Paper> paperList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                paper.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                paper.setId(id);
            }
            paperList = this.paperService.getAll(paper);

            List<ViewObject> vos = Lists.newArrayList();
            for (Paper item : paperList) {
                ViewObject vo = new ViewObject();
                vo.set("paper", item);
                vo.set("firstAuthor", userService.getByNumber(item.getFirstAuthor()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(paperList));
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
        String msg;
        try {
            boolean isSave = paper.getId() == null;
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = (isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                paper.setFirstAuthor(localUser.getNumber());
                if (isSave) {
                    paper.setCreatedId(localUser.getId());
                    paper.setCreatedDate(new Date());
                }
                boolean result = this.paperService.save(paper);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    Paper entity = this.paperService.getByPrimaryKey(paper.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(paper.getId(), EntityType.PAPER, null, status, EventType.REEDIT);
                    }
                }
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
                model.addAttribute("firstAuthorName", this.userService.getByNumber(paper.getFirstAuthor()).getName());
            }
        } catch (Exception e) {
            LOGGER.error("论文数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/paperEdit";
    }

    @RequestMapping(path = {"/paper/delete/{paperId}"})
    public String delete(@PathVariable("paperId") int paperId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.paperService.getByPrimaryKey(paperId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                Paper entity = this.paperService.getByPrimaryKey(paperId);
                boolean result = this.paperService.delete(paperId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(paperId, EntityType.PAPER, null, status, EventType.DELETE);
                    }
                }
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
                boolean result = this.paperService.setStatus(paperId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/paper";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(paperId, EntityType.PAPER, option, status, EventType.APPROVE);
            }
        } catch (Exception e) {
            msg = "论文审批出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/paper";
    }

}