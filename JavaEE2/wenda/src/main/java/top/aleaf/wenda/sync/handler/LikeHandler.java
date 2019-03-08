/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LikeHandler
 * Author:   郭新晔
 * Date:     2019/1/22 0022 22:28
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
import top.aleaf.wenda.model.Message;
import top.aleaf.wenda.service.MessageService;
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
 * @create 2019/1/22 0022
 */
@Component
public class LikeHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeHandler.class);
    @Autowired
    private MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        try {
            Message message = new Message();
            message.setHasRead(0);
            message.setToId(eventModel.getEventOwnerId());
            message.setFromId(WendaUtil.ADMIN_USER);
            message.setCreatedDate(new Date());
            message.setContent(String.format("你在【%s】问题下发布的评论被[%s]赞，http://localhost:8080",
                    eventModel.getValue("qName"), eventModel.getValue("actorName"),
                    eventModel.getValue("detailUrl")));
            int actorId = WendaUtil.ADMIN_USER;
            int ownerId = eventModel.getEventOwnerId();

            message.setConversationId(actorId < ownerId ? actorId + "_" + ownerId : ownerId + "_" + actorId);

            messageService.save(message);
        } catch (Exception e) {
            LOGGER.error("用户被赞事件处理异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}