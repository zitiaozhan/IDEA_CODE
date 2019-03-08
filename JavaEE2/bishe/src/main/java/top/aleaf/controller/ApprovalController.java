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
import top.aleaf.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/12 0012
 */
@Controller
public class ApprovalController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ApprovalController.class);
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyProjectService companyProjectService;
    @Autowired
    private GovernmentProjectService governmentProjectService;
    @Autowired
    private PrizeService prizeService;
    @Autowired
    private TeachingMaterialService teachingMaterialService;
    @Autowired
    private PatentService patentService;
    @Autowired
    private SoftwareWorkService softwareWorkService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private AcademicExchangeService academicExchangeService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private InfoTypeService infoTypeService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/approval"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          Approval approval, Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localRole == null || "teacher".equals(localRole.getDetail())) {
                return "redirect:/index";
            }
            List<Approval> approvalList = new ArrayList<>();
            if ("manager".equals(localRole.getDetail())) {
                approval.setUserId(localUser.getId());
            }
            approvalList = this.approvalService.getAll(approval);

            List<ViewObject> vos = new ArrayList<>();
            for (Approval item : approvalList) {
                ViewObject vo = new ViewObject();
                vo.set("approval", item);

                String entityName = "name";
                String entityType = this.infoTypeService.getByPrimaryKey(item.getEntityType()).getEntityName();
                switch (item.getEntityType()) {
                    case 1:
                        entityName = this.companyProjectService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 2:
                        entityName = this.governmentProjectService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 3:
                        entityName = this.prizeService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 4:
                        entityName = this.teachingMaterialService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 5:
                        entityName = this.patentService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 6:
                        entityName = this.softwareWorkService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 7:
                        entityName = this.lectureService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    case 8:
                        entityName = this.academicExchangeService.getByPrimaryKey(item.getEntityId()).getMeetingName();
                        break;
                    case 9:
                        entityName = this.paperService.getByPrimaryKey(item.getEntityId()).getName();
                        break;
                    default:
                        entityName = "项目已失联";
                }

                vo.set("entityName", entityName);
                vo.set("entityType", entityType);
                vo.set("userName", this.userService.getByPrimaryKey(item.getUserId()).getName());
                String result=item.getResult()==1?"审批成功":item.getResult()==2?"审批失败":"未知结果";
                vo.set("result", result);
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Approval>(approvalList));
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
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
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
            if (localRole==null){
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
        String msg = null;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !"smanager".equals(localRole.getDetail())) {
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