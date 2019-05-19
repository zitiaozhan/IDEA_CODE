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
import top.aleaf.service.AcademicExchangeService;
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
public class AcademicExchangeController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AcademicExchangeController.class);
    @Resource
    private AcademicExchangeService academicExchangeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private BaseController baseController;

    @RequestMapping(path = {"/academicExchange"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "part", required = false) boolean part,
                          @RequestParam(value = "id", required = false, defaultValue = "0") int id,
                          AcademicExchange academicExchange, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<AcademicExchange> academicExchangeList;
            if (!part && UserRoleEnum.TEACHER.getDesc().equals(localRole.getName())) {
                academicExchange.setCreatedId(localUser.getId());
            }
            if (id > 0) {
                academicExchange.setId(id);
            }
            academicExchangeList = this.academicExchangeService.getAll(academicExchange);

            List<ViewObject> vos = Lists.newArrayList();
            for (AcademicExchange item : academicExchangeList) {
                ViewObject vo = new ViewObject();
                vo.set("academicExchange", item);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(academicExchangeList));
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
        String msg;
        try {
            boolean isSave = academicExchange.getId() == null;
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            //TODO:修改项目权限有点问题，角色为教师时只有项目状态为已提交才能修改
            boolean limitOperate = isSave && UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail());
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                if (isSave) {
                    academicExchange.setCreatedId(localUser.getId());
                    academicExchange.setCreatedDate(new Date());
                }
                boolean result = this.academicExchangeService.save(academicExchange);
                msg = result ?
                        (isSave ? "添加成功!" : "更新成功!") :
                        (isSave ? "添加失败!" : "更新失败!");
                //发送到消息队列
                if (!isSave && result) {
                    AcademicExchange entity = this.academicExchangeService.getByPrimaryKey(academicExchange.getId());
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(academicExchange.getId(), EntityType.ACADEMIC_EXCHANGE, null, status, EventType.REEDIT);
                    }
                }
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
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }
            boolean limitOperate = UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail()) || (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail()) && !localUser.getId().equals(this.academicExchangeService.getByPrimaryKey(academicExchangeId).getCreatedId()));
            if (limitOperate) {
                msg = "受限制的操作";
            } else {
                AcademicExchange entity = this.academicExchangeService.getByPrimaryKey(academicExchangeId);
                boolean result = this.academicExchangeService.delete(academicExchangeId);
                msg = result ? "删除成功" : "删除失败";
                //发送到消息队列
                if (result && null != entity) {
                    int status = entity.getStatus();
                    if (status == ConstantUtil.PROJECT_STATUS_SUCCESS || ConstantUtil.PROJECT_STATUS_REFUSE == status) {
                        baseController.generalEventModelAndSend(academicExchangeId, EntityType.ACADEMIC_EXCHANGE, null, status, EventType.DELETE);
                    }
                }
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
                          @RequestParam("status") int status,
                          @RequestParam(value = "option", required = false) String option, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail())) {
                msg = "受限制的操作";
            } else {
                boolean result = this.academicExchangeService.setStatus(academicExchangeId, status);
                msg = result ? "审批成功" : "审批失败";
                if (!result) {
                    model.addAttribute("msg", msg);
                    return "/academicExchange";
                }
                if (com.google.common.base.Strings.isNullOrEmpty(option)) {
                    option = "审批通过";
                }

                //发送到消息队列
                baseController.generalEventModelAndSend(academicExchangeId, EntityType.ACADEMIC_EXCHANGE, option, status, EventType.APPROVE);
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