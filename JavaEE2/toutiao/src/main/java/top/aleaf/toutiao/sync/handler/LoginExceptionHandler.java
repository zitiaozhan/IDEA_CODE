/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LoginExceptionHandler
 * Author:   郭新晔
 * Date:     2019/1/10 0010 22:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.sync.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.toutiao.model.Message;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.service.MessageService;
import top.aleaf.toutiao.service.UserService;
import top.aleaf.toutiao.sync.EventHandler;
import top.aleaf.toutiao.sync.EventModel;
import top.aleaf.toutiao.sync.EventType;
import top.aleaf.toutiao.utils.MailSender;

import java.util.*;

/**
 * 〈〉
 *
 * @create 2019/1/10 0010
 */
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    @Override
    public void doHandle(EventModel eventModel) {
        //判断用户登录是否存在异常现象，如果存在则发送邮件或者站内信
        Message message = new Message();
        User actor = userService.getUser(eventModel.getActor());
        message.setToId(actor.getId());
        message.setStatus(0);
        message.setFromId(1);
        String mailContent = "用户【" + actor.getName() + "】于 " + eventModel.getExts().get("eventDate") + " 存在异常登录的情况";
        message.setContent(mailContent);
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        message.setConversationId("1_" + actor.getId());
        messageService.addMessage(message);
        //发送邮件
        Map<String, Object> model = new HashMap<>();
        model.put("mailContent", mailContent);
        model.put("website", "无涯");
        model.put("mailTitle", "登录异常");
        model.put("userName", actor.getName());
        model.put("mailUrl", "http://localhost:8080/msg/list");
        mailSender.sendWithHTMLTemplate(actor.getEmail(), "Login Exception!", "mails/welcome.html", model);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}