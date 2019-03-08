/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FollowHandler
 * Author:   郭新晔
 * Date:     2019/1/24 0024 16:19
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
import top.aleaf.wenda.model.EntityType;
import top.aleaf.wenda.model.Message;
import top.aleaf.wenda.model.Question;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.service.MessageService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventHandler;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventType;
import top.aleaf.wenda.util.WendaUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/24 0024
 */
@Component
public class CommentHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentHandler.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @Override
    public void doHandle(EventModel eventModel) {
        try {
            Message message = new Message();
            message.setHasRead(0);
            message.setToId(eventModel.getEventOwnerId());
            message.setFromId(WendaUtil.ADMIN_USER);
            message.setCreatedDate(new Date());

            User actor = this.userService.getById(eventModel.getActorId());

            if (eventModel.getEntityType().equals(EntityType.ENTITY_COMMENT.getValue())) {
                message.setContent(String.format("用户[%s]回复了你的评论", actor.getName()));
            } else if (eventModel.getEntityType().equals(EntityType.ENTITY_QUESTION.getValue())) {
                Question question = this.questionService.getById(eventModel.getEntityId());
                message.setContent(String.format("用户[%s]评论了你发布的问题【%s】", actor.getName(), question.getTitle()));
            }

            int actorId = WendaUtil.ADMIN_USER;
            int ownerId = eventModel.getEventOwnerId();

            message.setConversationId(actorId < ownerId ? actorId + "_" + ownerId : ownerId + "_" + actorId);

            messageService.save(message);
        } catch (Exception e) {
            LOGGER.error("评论事件处理异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}