/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MessageController
 * Author:   郭新晔
 * Date:     2018/12/11 0011 21:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.aleaf.toutiao.model.HostHolder;
import top.aleaf.toutiao.model.Message;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.model.ViewObject;
import top.aleaf.toutiao.service.MessageService;
import top.aleaf.toutiao.service.UserService;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/11 0011
 */
@Controller
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam(value = "fromId") int fromId
            , @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setHasRead(0);
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            this.messageService.addMessage(message);

            return ToutiaoUtil.getJSONString(message.getId());
        } catch (Exception e) {
            LOGGER.error("添加消息失败！" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "添加消息失败！");
        }
    }

    @RequestMapping(path = {"/msg/detail"}, method = RequestMethod.GET)
    public String getConversationDetail(@RequestParam("conversationId") String conversationId, Model model) {
        try {
            List<ViewObject> messages = new ArrayList<>();
            List<Message> messageList = this.messageService.getConversationDetail(conversationId, 0, 6);
            for (Message item : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", item);
                User user = this.userService.getUser(item.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
            this.messageService.setHasRead(this.hostHolder.getUser().getId(), conversationId);
            LOGGER.info("获取消息详情成功！");
        } catch (Exception e) {
            LOGGER.error("获取消息详情失败！" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/list"}, method = RequestMethod.GET)
    public String getConversationList(Model model) {
        try {
            User localUser = this.hostHolder.getUser();
            List<Message> list = this.messageService.getConversationList(localUser.getId(), 0, 6);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message item : list) {
                User targetUser = localUser.getId() == item.getFromId() ? this.userService.getUser(item.getToId()) : this.userService.getUser(item.getFromId());
                ViewObject conversation = new ViewObject();
                conversation.set("conversation", item);
                if (item.getConversationId() == null) {
                    continue;
                }
                conversation.set("unreadCount", this.messageService.getUnreadByConversationId(localUser.getId(), item.getConversationId()));
                conversation.set("userId", targetUser.getId());
                conversation.set("headUrl", targetUser.getHeadUrl());
                conversation.set("userName", targetUser.getName());
                conversations.add(conversation);
            }
            model.addAttribute("conversations", conversations);
            LOGGER.info("获取消息列表成功！");
        } catch (Exception e) {
            LOGGER.error("获取消息列表失败！" + e.getMessage());
        }
        return "letter";
    }
}