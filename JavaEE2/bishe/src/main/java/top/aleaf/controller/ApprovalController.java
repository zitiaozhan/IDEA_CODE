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
import top.aleaf.service.*;

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
public class ApprovalController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApprovalController.class);
    @Resource
    private ApprovalService approvalService;
    @Resource
    private UserService userService;

    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private GovernmentProjectService governmentProjectService;
    @Resource
    private PrizeService prizeService;
    @Resource
    private TeachingMaterialService teachingMaterialService;
    @Resource
    private PatentService patentService;
    @Resource
    private SoftwareWorkService softwareWorkService;
    @Resource
    private LectureService lectureService;
    @Resource
    private AcademicExchangeService academicExchangeService;
    @Resource
    private PaperService paperService;
    @Resource
    private HostHolder hostHolder;

    @RequestMapping(path = {"/approval"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Approval approval, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null || UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            List<Approval> approvalList;
            if (UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail())) {
                approval.setUserId(localUser.getId());
            }
            approvalList = this.approvalService.getAll(approval);

            List<ViewObject> vos = Lists.newArrayList();
            for (Approval item : approvalList) {
                ViewObject vo = new ViewObject();
                vo.set("approval", item);

                String entityName;
                String entityType = EntityType.valueMap.get(item.getEntityType()).getDesc();
                switch (EntityType.valueMap.get(item.getEntityType())) {
                    case COMPANY_PROJECT:
                        entityName = this.companyProjectService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case GOVERNMENT_PROJECT:
                        entityName = this.governmentProjectService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case PRIZE:
                        entityName = this.prizeService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case TEACHING_MATERIAL:
                        entityName = this.teachingMaterialService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case PATENT:
                        entityName = this.patentService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case SOFTWARE_WORK:
                        entityName = this.softwareWorkService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case LECTURE:
                        entityName = this.lectureService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case ACADEMIC_EXCHANGE:
                        entityName = this.academicExchangeService.getByPrimaryKey(item.getEntityId()).getMeetingName();
                        break;
                    case PAPER:
                        entityName = this.paperService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    default:
                        entityName = "项目已失联";
                }

                vo.set("entityName", entityName);
                vo.set("entityType", entityType);
                vo.set("userName", this.userService.getByPrimaryKey(item.getUserId()).getName());
                String result = item.getResult() == 1 ? "审批成功" : item.getResult() == 2 ? "审批驳回" : "未知结果";
                vo.set("result", result);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(approvalList));
            model.addAttribute("approval", approval);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("审批记录列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/approval";
    }

    @RequestMapping(path = {"/approval/edit"}, method = RequestMethod.POST)
    public String editApproval(Approval approval, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            boolean isSave = approval.getId() == null;
            msg = this.approvalService.save(approval) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("审批记录编辑出错");
            msg = "审批记录编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/approval";
    }

    @RequestMapping(path = {"/approval/forEdit"})
    public String forUpdate(@RequestParam(value = "approvalId", required = false) Integer approvalId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            if (approvalId != null) {
                Approval approval = this.approvalService.getByPrimaryKey(approvalId);
                model.addAttribute("approval", approval);
            }
        } catch (Exception e) {
            LOGGER.error("审批记录数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/approvalEdit";
    }

    @RequestMapping(path = {"/approval/delete/{approvalId}"})
    public String delete(@PathVariable("approvalId") int approvalId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            msg = this.approvalService.delete(approvalId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("审批记录删除出错");
            msg = "审批记录删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/approval";
    }

}