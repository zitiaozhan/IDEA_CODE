package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.aleaf.controller.convert.MessageConverter;
import top.aleaf.controller.vo.EntityProjectVO;
import top.aleaf.controller.vo.MessageVO;
import top.aleaf.controller.vo.WebResponse;
import top.aleaf.model.*;
import top.aleaf.model.enumModel.EntityType;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.model.option.Option;
import top.aleaf.service.*;
import top.aleaf.sync.EventModel;
import top.aleaf.sync.EventProducer;
import top.aleaf.sync.EventType;
import top.aleaf.utils.BisheUtil;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/12 0012
 */
@Controller
public class UserController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private MessageService messageService;
    @Resource
    private BaseController baseController;
    @Resource
    private ScoreService scoreService;
    @Resource
    private ApprovalService approvalService;
    @Resource
    private EventProducer eventProducer;

    @RequestMapping(path = {"/user"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg, User user, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            List<User> userList = this.userService.getAll(user);
            List<ViewObject> vos = Lists.newArrayList();
            for (User item : userList) {
                ViewObject vo = new ViewObject();
                vo.set("user", item);
                vo.set("roleName", roleService.getByPrimaryKey(item.getRoleId()).getName());
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(userList));
            model.addAttribute("user", user);

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
        } catch (Exception e) {
            LOGGER.error("用户列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/user";
    }

    /**
     * 用户首页
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/user/index"})
    public String home(Model model) {
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            if (localUser == null) {
                return "redirect:/index";
            }
            //用户主页的数据
            //公告
            List<MessageVO> bulletins = MessageConverter.convertMessageModelList(messageService.getByToId(ConstantUtil.MESSAGE_BULLETIN));
            model.addAttribute("bulletins", bulletins);
            //教师角色，最近发布项目10个，科技分来源，饼状图科技分
            if (UserRoleEnum.TEACHER.getValue().equals(localRole.getDetail())) {
                //最近发布项目10个
                model.addAttribute("recentlyReleased", baseController.recentlyReleased(localUser.getId()));
                //科技分来源
                Score score = this.scoreService.getByUserNumber(localUser.getNumber());
                List<EntityProjectVO> list = baseController.getAuthorScoreSource(score);
                model.addAttribute("scoreSource", list);
                model.addAttribute("satScore", score == null ? "0" : BisheUtil.doubleFormat(score.getSatScore(), ConstantUtil.DOUBLE_PATTERN));
            }
            //管理员角色，最近审批项目10个，分数排行榜，项目数量排行旁
            if (UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail()) || UserRoleEnum.MANAGER.getValue().equals(localRole.getDetail())) {
                //最近审批项目10个
                List<Approval> approvalList = this.approvalService.getByUserId(localUser.getId());
                if (approvalList.size() > 10) {
                    approvalList = approvalList.subList(0, 10);
                }
                approvalList = approvalList.stream().sorted(Comparator.comparing(Approval::getDate).reversed()).collect(Collectors.toList());

                model.addAttribute("approvalList", baseController.convertFromApprovalList(approvalList));
                //分数排行榜
                model.addAttribute("scoreTop", this.scoreService.getScoreTop());
                //数量排行榜
                model.addAttribute("numberTop", this.scoreService.getNumberTop());
            }

            model.addAttribute("localUser", localUser);
            model.addAttribute("localRole", localRole);
            return localRole.getDetail() + "/index";
        } catch (Exception e) {
            LOGGER.error("用户主页加载出错");
            e.printStackTrace();
        }
        return "redirect:/toLogin";
    }

    /**
     * 数据可视化
     *
     * @return Option
     */
    @RequestMapping(path = {"/show/data/{type}"})
    @ResponseBody
    public WebResponse<Option> dataVisualization(@PathVariable("type") int type,
                                                 @RequestParam(name = "userId", required = false, defaultValue = "0") int userId,
                                                 Model model) {
        User localUser = hostHolder.getUser();
        WebResponse<Option> result = new WebResponse<Option>();
        boolean condition = localUser == null || (type != 1 && type != 2);
        if (condition || !UserRoleEnum.valueMap.containsKey(localUser.getRoleId())) {
            result.setSuccess(false);
            result.setMsg("参数不合法");
            return result;
        }
        try {
            if (userId != 0) {
                //超管看所有
                User user = this.userService.getByPrimaryKey(userId);
                if (!UserRoleEnum.valueMap.containsKey(user.getRoleId())) {
                    result.setSuccess(false);
                    result.setMsg("该用户无可用数据！");
                    return result;
                }
                //判断角色
                localUser = user;
            }

            Option option;
            List<EntityProjectVO> list;
            if (UserRoleEnum.TEACHER.getId() == localUser.getRoleId()) {
                Score score = this.scoreService.getByUserNumber(localUser.getNumber());
                list = baseController.getAuthorScoreSource(score);
                if (1 == type) {
                    option = baseController.scoreDataVisualizationBar(list);
                } else {
                    option = baseController.scoreDataVisualizationPie(list, false);
                }
                result.setManager(false);
            } else {
                List<Approval> approvalList = this.approvalService.getByUserId(localUser.getId());
                list = baseController.convertFromApprovalList(approvalList);
                option = baseController.scoreDataVisualizationPie(list, true);
                result.setManager(true);
            }

            result.setSuccess(true);
            result.setData(option);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("未查到该用户的可用数据！");
            result.setException(e);
        }
        return result;
    }

    @RequestMapping(path = {"/user/edit"}, method = RequestMethod.POST)
    public String editRole(User user, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            boolean isSave = user.getId() == null;

            msg = this.userService.save(user) ?
                    (isSave ? "添加成功!" : "更新成功!") :
                    (isSave ? "添加失败!" : "更新失败!");
        } catch (Exception e) {
            LOGGER.error("用户编辑出错");
            msg = "用户编辑出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/user";
    }

    @RequestMapping(path = {"/user/forEdit"})
    public String forUpdate(@RequestParam(value = "userId", required = false) Integer userId,
                            Model model) {
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            if (userId != null) {
                User user = this.userService.getByPrimaryKey(userId);
                model.addAttribute("user", user);
            }
            List<Role> roleList = this.roleService.getAll(new Role());
            model.addAttribute("roleList", roleList);
        } catch (Exception e) {
            LOGGER.error("帐号数据准备出错");
            e.printStackTrace();
        }
        return "smanager/edit/userEdit";
    }

    @RequestMapping(path = {"/user/delete/{userId}"})
    public String delete(@PathVariable("userId") int userId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null || !UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                return "redirect:/index";
            }

            boolean result = this.userService.delete(userId);
            msg = result ? "删除成功" : "删除失败";
            if (result) {
                EventModel eventModel = new EventModel();
                eventModel.setActorId(localUser.getId());
                eventModel.setEventType(EventType.LOGOUT_USER);
                eventModel.setEntityType(EntityType.USER);
                eventModel.setEntityId(userId);
                this.eventProducer.fireEvent(eventModel);
            }
        } catch (Exception e) {
            LOGGER.error("用户删除出错");
            msg = "用户删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/user";
    }

    @RequestMapping(path = "/user/setting")
    public String userSetting(@RequestParam(value = "oldPassword", required = false) String oldPassword,
                              User user,
                              Model model) {
        String msg = "";
        try {
            User localUser = hostHolder.getUser();
            Role localRole = hostHolder.getRole();
            model.addAttribute("localUser", localUser);
            model.addAttribute("localRole", localRole);

            if (oldPassword != null) {
                if (this.userService.validatePwd(localUser, oldPassword)) {
                    user.setPassword(BisheUtil.MD5(user.getPassword() + localUser.getSalt()));
                    msg = this.userService.save(user) ? "修改密码成功" : "修改密码失败";
                } else {
                    msg = "密码输入错误";
                }
            } else {
                if (user.getId() != null) {
                    msg = this.userService.save(user) ? "保存成功" : "保存失败";
                    model.addAttribute("localUser", this.userService.getByPrimaryKey(user.getId()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("用户信息设置出错");
            msg = "用户信息设置出错";
            e.printStackTrace();
        }
        if (Strings.isNotBlank(msg)) {
            model.addAttribute("msg", msg);
        }
        return "smanager/edit/userSetting";
    }

    @PostMapping("/user/batchReg")
    public String batchRegister(User user, @RequestParam("numberFrom") long numberFrom,
                                @RequestParam("numberTo") long numberTo, Model model) {
        String msg;
        try {
            //入参判断
            if (numberFrom > numberTo) {
                msg = "参数不合法";
                model.addAttribute("msg", msg);
                return "/user";
            }

            //批量注册
            List<User> userList = Lists.newArrayList();
            for (long i = numberFrom; i <= numberTo; i++) {
                User tmp = new User();
                tmp.setRoleId(user.getRoleId());
                tmp.setProfession(user.getProfession());
                tmp.setNumber(String.valueOf(i));
                tmp.setName(String.valueOf(i));
                tmp.setPassword(user.getPassword());
                tmp.setMail(i + "@163.com");
                userList.add(tmp);
            }
            userService.batchInsert(userList);
            msg = "账户批量注册成功";
        } catch (Exception e) {
            LOGGER.error("账户批量注册失败");
            e.printStackTrace();
            msg = "账户批量注册失败";
        }
        model.addAttribute("msg", msg);
        return "/user";
    }

}