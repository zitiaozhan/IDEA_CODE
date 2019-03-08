/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LikeController
 * Author:   郭新晔
 * Date:     2019/1/22 0022 15:19
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.aleaf.wenda.model.*;
import top.aleaf.wenda.service.CommentService;
import top.aleaf.wenda.service.LikeService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventProducer;
import top.aleaf.wenda.sync.EventType;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.WendaUtil;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
@Controller
public class LikeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisAdapter.class);

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/like"}, method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("entityId") int entityId,
                       @RequestParam("entityType") String entityType) {
        try {
            User user = this.hostHolder.getUser();
            if (user == null) {
                return WendaUtil.getJSONString(999);
            }
            long likeCount = this.likeService.like(user.getId(), entityId, entityType);

            if (likeService.isLike(user.getId(), entityId, entityType)) {
                EventModel eventModel = new EventModel();
                eventModel.setActorId(user.getId())
                        .setEntityId(entityId)
                        .setEntityType(entityType)
                        .addExt("actorName", user.getName())
                        .setEventType(EventType.LIKE);
                if (entityType.equals(EntityType.ENTITY_COMMENT.getValue())) {
                    Comment comment = this.commentService.getById(entityId);
                    User commentUser = this.userService.getById(comment.getUserId());

                    eventModel.setEventOwnerId(commentUser.getId());
                    if (comment.getEntityType().equals(EntityType.ENTITY_QUESTION.getValue())) {
                        Question question = this.questionService.getById(comment.getEntityId());
                        eventModel.addExt("qName", question.getTitle());
                        eventModel.addExt("detailUrl", "/question/" + question.getId());
                    }
                }
                this.eventProducer.fireEvent(eventModel);
            }

            return WendaUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            LOGGER.error("点赞功能出错：" + e.getMessage());
            return WendaUtil.getJSONString(1, "点赞功能出错");
        }
    }

}