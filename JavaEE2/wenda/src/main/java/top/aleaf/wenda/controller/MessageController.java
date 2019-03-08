/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MessageController
 * Author:   郭新晔
 * Date:     2019/1/20 0020 22:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.aleaf.wenda.model.HostHolder;
import top.aleaf.wenda.model.Message;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.model.ViewObject;
import top.aleaf.wenda.service.MessageService;
import top.aleaf.wenda.service.SensitiveFilterService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.util.WendaUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/20 0020
 */
@Controller
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SensitiveFilterService sensitiveFilterService;

    @RequestMapping(path = {"/msg/list"})
    public String showMessageList(Model model) {
        try {
            User user = this.hostHolder.getUser();
            List<Message> messageList = this.messageService.getConversationList(user.getId());
            List<ViewObject> vos = new ArrayList<>();
            for (Message item : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", item);
                vo.set("unreadCount", this.messageService.getUnreadCount(item.getConversationId(), user.getId()));
                int anotherId = item.getFromId().equals(user.getId()) ? item.getToId() : item.getFromId();
                User another = this.userService.getById(anotherId);
                vo.set("userId", another.getId());
                vo.set("headUrl", another.getHeadUrl());
                vo.set("userName", another.getName());
                vos.add(vo);
            }
            model.addAttribute("conversations", vos);
            return "letter";
        } catch (Exception e) {
            LOGGER.error("消息列表获取失败：" + e.getMessage());
            e.printStackTrace();
            return "redirect:/index";
        }
    }

    @RequestMapping(path = {"/msg/detail"})
    public String showConversationDetail(@RequestParam(value = "conversationId") String conversationId,
                                         Model model) {
        try {
            List<Message> messageList = this.messageService.getAll(
                    new Message().setConversationId(conversationId));
            List<ViewObject> vos = new ArrayList<>();
            for (Message item : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", item);
                User user = this.userService.getById(item.getFromId());
                vo.set("user", user);
                vos.add(vo);
            }
            model.addAttribute("msg", vos);

            this.messageService.clearUnreadCount(conversationId, this.hostHolder.getUser().getId());

            return "letterDetail";
        } catch (Exception e) {
            LOGGER.error("消息详情获取失败: " + e.getMessage());
            return "/msg/list";
        }
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            User from = hostHolder.getUser();
            if (from == null) {
                return WendaUtil.getJSONString(999, "用户未登录");
            }
            User to = this.userService.getByName(toName);
            if (to == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }
            toName = sensitiveFilterService.filterSensitiveWord(toName);
            content = sensitiveFilterService.filterSensitiveWord(content);
            Message message = new Message();
            message.setConversationId(
                    to.getId() < from.getId() ? to.getId() + "_" + from.getId() : from.getId() + "_" + to.getId());
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setFromId(from.getId());
            message.setToId(to.getId());
            message.setHasRead(0);
            this.messageService.save(message);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            LOGGER.error("发信失败：" + e.getMessage());
            return WendaUtil.getJSONString(1, "发信异常");
        }
    }
}