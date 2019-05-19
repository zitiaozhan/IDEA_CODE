/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MessageController
 * Author:   郭新晔
 * Date:     2019/2/21 0021 16:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.controller.convert.MessageConverter;
import top.aleaf.controller.vo.MessageVO;
import top.aleaf.model.*;
import top.aleaf.model.enumModel.UserRoleEnum;
import top.aleaf.service.MessageService;
import top.aleaf.service.RoleService;
import top.aleaf.service.SensitiveFilterService;
import top.aleaf.service.UserService;
import top.aleaf.utils.ConstantUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/21 0021
 */
@Controller
public class MessageController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private SensitiveFilterService filterService;

    @RequestMapping(path = {"/message"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "userName", required = false) String userName, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Message> messageList;
            if (!com.google.common.base.Strings.isNullOrEmpty(userName)) {
                model.addAttribute("userName", userName);
                messageList = this.messageService.searchByName(userName, localUser.getId());
            } else {
                messageList = this.messageService.getAll(new Message(), localUser.getId());
            }
            List<ViewObject> vos = Lists.newArrayList();
            for (Message item : messageList) {
                ViewObject vo = new ViewObject();
                int anotherId = item.getFromId().equals(localUser.getId()) ? item.getToId() : item.getFromId();
                /*if (ConstantUtil.MESSAGE_BULLETIN == anotherId) {
                    continue;
                }*/
                User anotherUser = this.userService.getByPrimaryKey(anotherId);
                Role anotherRole = this.roleService.getByPrimaryKey(anotherUser.getRoleId());
                int unread = this.messageService.getUnreadCount(item.getConversationId(), localUser.getId());
                vo.set("message", item);
                vo.set("anotherName", anotherUser.getName() + "(" + anotherRole.getName() + ")");
                vo.set("unread", unread);
                vos.add(vo);
            }

            model.addAttribute("vos", vos);
            model.addAttribute("nowDate", new Date());
            model.addAttribute("msg", msg);

            List<MessageVO> messageVOList = MessageConverter.convertMessageModelList(messageList);

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<>(messageVOList));

            model.addAttribute("msg", msg);
        } catch (Exception e) {
            LOGGER.error("消息列表加载出错");
            e.printStackTrace();
        }
        return "smanager/view/message";
    }

    @RequestMapping(path = {"/message/show"})
    public String show(@RequestParam("conversationId") String conversationId,
                       Model model, @RequestParam(value = "toId", required = false) Integer toId,
                       @RequestParam(value = "content", required = false) String content) {
        try {
            User localUser = this.hostHolder.getUser();
            if (localUser == null) {
                return "redirect:/index";
            }
            if (toId != null && !"".equals(content.trim())) {
                int fromId = localUser.getId();
                Message message = new Message();
                message.setToId(toId);
                message.setFromId(fromId);

                content = filterService.filterSensitiveWord(content);

                message.setContent(content);
                String newConversationId = fromId < toId ? fromId + "_" + toId : toId + "_" + fromId;
                message.setConversationId(newConversationId);
                message.setCreatedDate(new Date());
                message.setHasRead(0);
                this.messageService.save(message);
            }
            //清理未读消息数量
            int unread = this.messageService.getUnreadCount(conversationId, localUser.getId());
            if (unread > 0) {
                this.messageService.clearUnreadCount(conversationId, localUser.getId());
            }
            //完成聊天功能
            List<MessageVO> messageList = MessageConverter.convertMessageModelList(this.messageService.getByConversationId(conversationId));
            if (messageList.size() > 0) {
                int anotherId = messageList.get(0).getFromId().equals(localUser.getId()) ? messageList.get(0).getToId() : messageList.get(0).getFromId();
                User anotherUser = this.userService.getByPrimaryKey(anotherId);
                model.addAttribute("anotherUser", anotherUser);
            }
            model.addAttribute("conversationId", conversationId);
            model.addAttribute("messageList", messageList);
        } catch (Exception e) {
            LOGGER.error("消息加载出错");
            e.printStackTrace();
        }
        return "smanager/view/chatDetail";
    }

    @RequestMapping(path = {"/message/delete/{messageId}"})
    public String delete(@PathVariable("messageId") int messageId, Model model) {
        String msg;
        try {
            Role localRole = hostHolder.getRole();
            if (localRole == null) {
                return "redirect:/index";
            }
            msg = this.messageService.deleteById(messageId) ? "删除成功" : "删除失败";
        } catch (Exception e) {
            LOGGER.error("消息删除出错");
            msg = "消息删除出错";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/message";
    }

    /**
     * 发消息
     *
     * @param toId
     * @param model
     * @return
     */
    @RequestMapping(path = {"/message/send"})
    public String sendMessage(@RequestParam("toId") int toId, Model model) {
        try {
            User localUser = this.hostHolder.getUser();
            if (null == localUser) {
                return "redirect:/index";
            }
            int fromId = localUser.getId();
            String conversationId = fromId < toId ? fromId + "_" + toId : toId + "_" + fromId;
            List<Message> messageList = this.messageService.getByConversationId(conversationId);
            User anotherUser = this.userService.getByPrimaryKey(toId);
            model.addAttribute("anotherUser", anotherUser);
            model.addAttribute("conversationId", conversationId);
            model.addAttribute("messageList", messageList);
        } catch (Exception e) {
            LOGGER.error("发送消息模块加载出错");
            e.printStackTrace();
        }
        return "smanager/view/chatDetail";
    }

    /**
     * 发送公告
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/message/addBulletin"})
    public String addBulletin(@RequestParam("content") String content, Model model) {
        String msg;
        try {
            User localUser = hostHolder.getUser();
            if (null == localUser) {
                return "redirect:/index";
            }
            Role localRole = hostHolder.getRole();

            int fromId = localUser.getId();
            if (UserRoleEnum.ADMIN.getValue().equals(localRole.getDetail())) {
                Message message = new Message();
                message.setFromId(fromId);
                message.setContent(content);
                message.setToId(ConstantUtil.MESSAGE_BULLETIN);
                message.setConversationId(fromId + "_" + ConstantUtil.MESSAGE_BULLETIN);
                message.setCreatedDate(new Date());
                message.setHasRead(1);
                msg = messageService.save(message) ? "公告发布成功" : "公告发布失败";
            } else {
                msg = "无此权限";
            }
        } catch (Exception e) {
            LOGGER.error("发送公告异常");
            msg = "发送公告异常";
            e.printStackTrace();
        }
        model.addAttribute("msg", msg);
        return "/message";
    }
}