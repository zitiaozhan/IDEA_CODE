/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LoginHandler
 * Author:   郭新晔
 * Date:     2019/1/23 0023 15:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.sync.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.service.MailService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventHandler;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventType;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈〉
 *
 * @create 2019/1/23 0023
 */
@Component
public class LoginHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @Override
    public void doHandle(EventModel eventModel) {
        try {
            User webUser = this.userService.getById(eventModel.getEventOwnerId());
            Map<String, Object> model = new HashMap<>();
            model.put("webName", "无涯");
            model.put("mailTitle", "登录异常");
            model.put("userName", webUser.getName());
            model.put("mailUrl", "http://localhost:8080/user/" + webUser.getId());
            model.put("mailContent", String.format(
                    "用户【%s】于 %s 存在异常登录的情况，请立即查看",
                    webUser.getName(),
                    new SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS").format(new Date())
            ));

            boolean res=this.mailService.sendWithHTMLTemplate(webUser.getMail(),
                    "Login Exception", "mail.ftl", model);
            if (res){
                LOGGER.info("邮件发送成功");
            }else {
                LOGGER.warn("邮件发送失败");
            }
        } catch (Exception e) {
            LOGGER.error("邮件发送失败");
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}