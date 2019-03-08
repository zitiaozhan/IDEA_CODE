/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LikeHandler
 * Author:   郭新晔
 * Date:     2019/1/9 0009 16:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.sync.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.toutiao.model.Message;
import top.aleaf.toutiao.model.News;
import top.aleaf.toutiao.model.User;
import top.aleaf.toutiao.service.MessageService;
import top.aleaf.toutiao.service.NewsService;
import top.aleaf.toutiao.service.UserService;
import top.aleaf.toutiao.sync.EventHandler;
import top.aleaf.toutiao.sync.EventModel;
import top.aleaf.toutiao.sync.EventType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/9 0009
 */
@Component
public class LikeHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeHandler.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        String conversationId = model.getActor() < model.getEntityOwnerId() ?
                model.getActor() + "_" + model.getEntityOwnerId() : model.getEntityOwnerId() + "_" + model.getActor();
        User actor = userService.getUser(model.getActor());
        News news = newsService.getNewsById(model.getEntityId());
        message.setConversationId(conversationId);
        message.setHasRead(0);
        message.setCreatedDate(new Date());
        message.setContent("你发布的资讯【" + news.getTitle() + "】" +
                "收到来自用户[" + actor.getName() + "]的赞    http://localhost:8080/news/" + news.getId());
        message.setToId(model.getEntityOwnerId());
        message.setFromId(model.getActor());
        message.setStatus(0);
        this.messageService.addMessage(message);
        LOGGER.info(model.getActor() + " 喜欢了 " + model.getEntityOwnerId() + " 发布的 " + news.getTitle());
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}