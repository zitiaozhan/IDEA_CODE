/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CommentController
 * Author:   郭新晔
 * Date:     2019/1/21 0021 17:50
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
import top.aleaf.wenda.model.*;
import top.aleaf.wenda.service.CommentService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.SensitiveFilterService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventProducer;
import top.aleaf.wenda.sync.EventType;

import java.util.Date;

/**
 * 〈〉
 *
 * @create 2019/1/21 0021
 */
@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private SensitiveFilterService sensitiveFilterService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("entityType") String entityType,
                             @RequestParam("entityId") int entityId,
                             @RequestParam(value = "next", required = false) String next) {
        try {
            User user = this.hostHolder.getUser();
            next = next == null ? "/index" : next;
            content = sensitiveFilterService.filterSensitiveWord(content);
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(entityId);
            comment.setEntityType(entityType);
            if (user == null) {
                return "redirect:" + next;
            }

            if (entityType.equals(EntityType.ENTITY_QUESTION.getValue())) {
                Question question = this.questionService.getById(entityId);
                if (question != null) {
                    this.eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                            .setEventOwnerId(question.getUserId())
                            .setActorId(user.getId())
                            .setEntityId(entityId)
                            .setEntityType(entityType));
                }
            }

            comment.setUserId(this.hostHolder.getUser().getId());
            this.commentService.save(comment);
            return "redirect:" + next;
        } catch (Exception e) {
            LOGGER.error("新增评论异常：" + e.getMessage());
        }
        return "redirect:" + next;
    }
}