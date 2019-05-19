package top.aleaf.service;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.aleaf.mapper.LoginTicketMapper;
import top.aleaf.mapper.RoleMapper;
import top.aleaf.mapper.UserMapper;
import top.aleaf.model.LoginTicket;
import top.aleaf.model.Role;
import top.aleaf.model.User;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.utils.BisheUtil;
import top.aleaf.utils.ConstantUtil;
import top.aleaf.utils.DecryptUtil;
import top.aleaf.utils.GeneralExample;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private LoginTicketMapper ticketMapper;
    @Resource
    private MailService mailService;
    @Resource
    private SensitiveFilterService sensitiveFilterService;

    public Map<String, String> reg(String mail) {
        Map<String, String> map = new HashMap<>();
        if (Strings.isNullOrEmpty(mail)) {
            map.put("msg", "邮箱不能为空");
            return map;
        }
        if (!mail.matches("[\\w\\-]+@[a-z0-9A-Z]+(\\.[a-zA-Z]{2,4}){1,2}")) {
            map.put("msg", "不规范的邮箱");
            return map;
        }
        if (getByMail(mail) != null) {
            map.put("msg", "该邮箱已被注册");
            return map;
        }
        Map<String, Object> model = new HashMap<>();
        String validate = UUID.randomUUID().toString().substring(1, 7);
        String mi = DecryptUtil.encrypt(validate + ConstantUtil.MAIL_VALIDATE_SPLIT + mail);
        model.put("activeUrl", "http://localhost:8080/back/regValidate?activeParam=" + mi);
        if (mailService.sendWithHTMLTemplate(mail, "帐号注册激活", "mailActive.ftl", model)) {
            map.put("validate", validate);
        } else {
            map.put("msg", "邮件发送失败");
        }
        return map;
    }

    /**
     * 为重置密码做准备
     *
     * @param mail 邮箱
     * @return 结果
     */
    public Map<String, String> forRepassword(String mail) {
        Map<String, String> map = new HashMap<>();
        if (Strings.isNullOrEmpty(mail)) {
            map.put("msg", "邮箱不能为空");
            return map;
        }
        if (!mail.matches("[\\w\\-]+@[a-z0-9A-Z]+(\\.[a-zA-Z]{2,4}){1,2}")) {
            map.put("msg", "不规范的邮箱");
            return map;
        }
        User user = getByMail(mail);
        if (null == user) {
            map.put("msg", "未注册过的邮箱");
            return map;
        }

        Map<String, Object> model = new HashMap<>();
        String validate = UUID.randomUUID().toString().substring(1, 7);
        String mi = DecryptUtil.encrypt(validate + ConstantUtil.MAIL_VALIDATE_SPLIT + mail);
        model.put("activeUrl", "http://localhost:8080/back/repasswordValidate?activeParam=" + mi);
        if (mailService.sendWithHTMLTemplate(mail, "帐号找回密码", "mailActive.ftl", model)) {
            map.put("validate", validate);
        } else {
            map.put("msg", "邮件发送失败");
        }
        return map;
    }

    public boolean repassword(String mail, String password) {
        User user = getByMail(mail);
        if (null == user) {
            return false;
        }
        user.setSalt(UUID.randomUUID().toString().substring(1, 8));
        user.setPassword(BisheUtil.MD5(password + user.getSalt()));
        return save(user);
    }

    public Map<String, String> register(User user) {
        Map<String, String> map = new HashMap<>();
        if (Strings.isNullOrEmpty(user.getNumber())) {
            map.put("msg", "编号不能为空");
            return map;
        }
        if (user.getPassword() == null || user.getPassword().trim().length() <= 0) {
            map.put("msg", "密码不能为空");
            return map;
        }
        if (user.getPassword().length() < 6) {
            map.put("msg", "密码长度必须5位以上");
            return map;
        }
        if (Strings.isNullOrEmpty(user.getMail())) {
            map.put("msg", "邮箱不能为空");
            return map;
        }
        if (!user.getMail().matches("[\\w\\-]+@[a-z0-9A-Z]+(\\.[a-zA-Z]{2,4}){1,2}")) {
            map.put("msg", "不规范的邮箱");
            return map;
        }
        if (getByMail(user.getMail()) != null) {
            map.put("msg", "该邮箱已被注册");
            return map;
        }
        if (user.getName().length() > 15) {
            user.setName(user.getName().substring(0, 16));
        }
        user.setName(this.sensitiveFilterService.filterSensitiveWord(user.getName()));

        user.setSalt(UUID.randomUUID().toString().substring(1, 8));
        user.setPassword(BisheUtil.MD5(user.getPassword() + user.getSalt()));
        user.setRegDate(new Date());
        user.setRoleId(roleMapper.selectOne(new Role().setName(UserRoleEnum.TEACHER.getDesc())).getId());
        int lineNum = this.userMapper.insert(user);

        if (lineNum > 0) {
            String ticket = this.addLoginTicket(user.getId());
            map.put("ticket", ticket);
        }

        return map;
    }

    public Map<String, String> login(String number, String password) {
        Map<String, String> map = new HashMap<>();

        if (Strings.isNullOrEmpty(number)) {
            map.put("msg", "编号为空");
            return map;
        }
        if (password == null || password.trim().length() <= 0) {
            map.put("msg", "密码为空");
            return map;
        }
        User us = getByNumber(number);
        if (us != null && Objects.equals(BisheUtil.MD5(password + us.getSalt()), us.getPassword())) {
            Role role = new Role();
            role.setId(us.getRoleId());
            map.put("roleName", this.roleMapper.selectOne(role).getDetail());
            String ticket = this.addLoginTicket(us.getId());
            map.put("ticket", ticket);
        } else {
            map.put("msg", "编号或密码错误");
        }

        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date date = new Date();
        date.setTime(date.getTime() + 3 * 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticketMapper.insert(ticket);
        return ticket.getTicket();
    }

    public boolean logout(String ticket) {
        LoginTicket loginTicket = this.ticketMapper.selectOne(new LoginTicket().setTicket(ticket));
        loginTicket.setStatus(1);
        return this.ticketMapper.updateByPrimaryKeySelective(loginTicket) > 0;
    }

    public boolean validatePwd(User us, String pwd) {
        return pwd != null && Objects.equals(BisheUtil.MD5(pwd + us.getSalt()), us.getPassword());
    }

    public boolean save(User user) {
        if (user.getName() != null) {
            user.setName(this.sensitiveFilterService.filterSensitiveWord(user.getName()));
        }
        if (user.getId() == null) {
            user.setSalt(UUID.randomUUID().toString().substring(1, 8));
            user.setPassword(BisheUtil.MD5(user.getPassword() + user.getSalt()));
            user.setRegDate(new Date());
            return this.userMapper.insert(user) > 0;
        } else {
            String salt = getByPrimaryKey(user.getId()).getSalt();
            user.setPassword(BisheUtil.MD5(user.getPassword() + salt));
            return this.userMapper.updateByPrimaryKeySelective(user) > 0;
        }
    }

    /**
     * 批量插入
     *
     * @param userList 用户列表
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchInsert(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        userList.forEach(user -> {
            user.setName(this.sensitiveFilterService.filterSensitiveWord(user.getName()));
            user.setSalt(UUID.randomUUID().toString().substring(1, 8));
            user.setPassword(BisheUtil.MD5(user.getPassword() + user.getSalt()));
            user.setRegDate(new Date());
        });
        this.userMapper.insertList(userList);
    }

    public boolean delete(User user) {
        if (user != null && user.getId() != null) {
            user.setStatus(1);
            return this.userMapper.updateByPrimaryKeySelective(user) > 0;
        }
        return false;
    }

    public boolean delete(int id) {
        User user = new User();
        user.setId(id);
        user.setStatus(1);
        return this.userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    public User getByPrimaryKey(int id) {
        return this.userMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(User.class, " and id=" + id, true));
    }

    public User getByMail(String mail) {
        return this.userMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(
                        User.class, " and mail='" + mail + "' ", true));
    }

    /**
     * 通过用户编号查询
     *
     * @param number
     * @return
     */
    public User getByNumber(String number) {
        return this.userMapper.selectOneByExample(
                GeneralExample.getBaseAndConditionExample(
                        User.class, " and number='" + number + "' ", true));
    }

    Integer getUserCount() {
        return this.userMapper.selectCount(new User());
    }

    public List<User> getAll(User user) {
        if (user.getPage() != null && user.getRows() != null) {
            PageHelper.startPage(user.getPage(), user.getRows());
        }
        StringBuilder builder = new StringBuilder("");
        if (user.getName() != null) {
            builder.append(" and name like '%").append(user.getName()).append("%' ");
        }
        if (user.getMail() != null) {
            builder.append(" and mail like '%").append(user.getMail()).append("%' ");
        }
        if (user.getProfession() != null) {
            builder.append(" and profession='").append(user.getProfession()).append("' ");
        }
        return userMapper.selectByExample(GeneralExample.getBaseAndConditionExample(
                User.class, builder.toString(), true
        ));
    }

    /**
     * 按用户编号查询
     *
     * @param numberList 用户编号列表
     * @return 结果
     */
    public List<User> getUserByNumberList(List<String> numberList) {
        return this.userMapper.selectByNumberList(numberList);
    }

    /**
     * 数量
     *
     * @param roleId 用户角色
     * @return 结果
     */
    public int getAllCount(int roleId) {
        if (-1 == roleId) {
            return this.userMapper.selectCount(new User().setStatus(null));
        }
        return this.userMapper.selectCount(new User().setStatus(null).setRoleId(roleId));
    }
}