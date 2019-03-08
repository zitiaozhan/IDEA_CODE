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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.aleaf.model.*;
import top.aleaf.service.MessageService;
import top.aleaf.service.RoleService;
import top.aleaf.service.SensitiveFilterService;
import top.aleaf.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/21 0021
 */
@Controller
public class MessageController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SensitiveFilterService filterService;

    @RequestMapping(path = {"/message"})
    public String showAll(@RequestParam(value = "msg", required = false) String msg,
                          String userName, Model model) {
        try {
            Role localRole = hostHolder.getRole();
            User localUser = hostHolder.getUser();
            if (localRole == null) {
                return "redirect:/index";
            }

            List<Message> messageList = new ArrayList<>();
            if (userName != null && !"".equals(userName)) {
                model.addAttribute("userName", userName);
                messageList = this.messageService.searchByName(userName, localUser.getId());
            } else {
                messageList = this.messageService.getAll(new Message(), localUser.getId());
            }
            List<ViewObject> vos = new ArrayList<>();
            for (Message item : messageList) {
                ViewObject vo = new ViewObject();
                int anotherId = item.getFromId().equals(localUser.getId()) ? item.getToId() : item.getFromId();
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

            //分页实现
            model.addAttribute("pageInfo", new PageInfo<Message>(messageList));

            if (msg != null) {
                model.addAttribute("msg", msg);
            }
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
            List<Message> messageList = this.messageService.getByConversationId(conversationId);
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
        String msg = null;
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
}