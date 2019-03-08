/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FollowController
 * Author:   郭新晔
 * Date:     2019/1/23 0023 22:47
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
import org.springframework.web.bind.annotation.*;
import top.aleaf.wenda.model.*;
import top.aleaf.wenda.service.CommentService;
import top.aleaf.wenda.service.FollowService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventProducer;
import top.aleaf.wenda.sync.EventType;
import top.aleaf.wenda.util.WendaUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/23 0023
 */
@Controller
public class FollowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private FollowService followService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/followUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        User localUser = hostHolder.getUser();
        if (localUser == null) {
            return WendaUtil.getJSONString(999);
        }
        boolean res = this.followService.follow(localUser.getId(), userId, EntityType.ENTITY_USER);
        if (res) {
            this.eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                    .setEventOwnerId(userId)
                    .setActorId(localUser.getId())
                    .setEntityId(userId)
                    .setEntityType(EntityType.ENTITY_USER.getValue()));
        }
        return WendaUtil.getJSONString(res ? 0 : 1,
                String.valueOf(this.followService.getFolloweeCount(localUser.getId(), EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/unfollowUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        User localUser = hostHolder.getUser();
        if (localUser == null) {
            return WendaUtil.getJSONString(999);
        }
        boolean res = this.followService.unfollow(localUser.getId(), userId, EntityType.ENTITY_USER);
        if (res) {
            this.eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                    .setEventOwnerId(userId)
                    .setActorId(localUser.getId())
                    .setEntityId(userId)
                    .setEntityType(EntityType.ENTITY_USER.getValue()));
        }
        return WendaUtil.getJSONString(res ? 0 : 1,
                String.valueOf(this.followService.getFolloweeCount(localUser.getId(), EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/followQuestion"}, method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId) {
        User localUser = hostHolder.getUser();
        if (localUser == null) {
            return WendaUtil.getJSONString(999);
        }
        Question question = this.questionService.getById(questionId);
        if (question == null) {
            return WendaUtil.getJSONString(1, "问题不存在");
        }
        boolean res = this.followService.follow(localUser.getId(), questionId, EntityType.ENTITY_QUESTION);
        Map<String, Object> info = new HashMap<>();
        if (res) {
            User questionOwner = this.userService.getById(question.getUserId());
            this.eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                    .setEventOwnerId(questionOwner.getId())
                    .setActorId(localUser.getId())
                    .setEntityId(questionId)
                    .setEntityType(EntityType.ENTITY_QUESTION.getValue()));

            info.put("headUrl", localUser.getHeadUrl());
            info.put("name", localUser.getName());
            info.put("id", localUser.getId());
            info.put("count", followService.getFollowerCount(questionId, EntityType.ENTITY_QUESTION));
        }
        return WendaUtil.getJSONString(res ? 0 : 1,info);
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = RequestMethod.POST)
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        User localUser = hostHolder.getUser();
        if (localUser == null) {
            return WendaUtil.getJSONString(999);
        }
        Question question = this.questionService.getById(questionId);
        if (question == null) {
            return WendaUtil.getJSONString(1, "问题不存在");
        }
        boolean res = this.followService.unfollow(localUser.getId(), questionId, EntityType.ENTITY_QUESTION);
        Map<String, Object> info = new HashMap<>();
        if (res) {
            this.eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                    .setEventOwnerId(question.getUserId())
                    .setActorId(localUser.getId())
                    .setEntityId(questionId)
                    .setEntityType(EntityType.ENTITY_QUESTION.getValue()));

            info.put("id", hostHolder.getUser().getId());
            info.put("count", followService.getFollowerCount(questionId,EntityType.ENTITY_QUESTION));
        }
        return WendaUtil.getJSONString(res ? 0 : 1,info);
    }

    /**
     * 某人的粉丝
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}/followers"}, method = RequestMethod.GET)
    public String showFollowers(@PathVariable("userId") int userId, Model model) {
        /*User localUser = this.hostHolder.getUser();
        if (localUser == null) {
            return "login";
        }*/
        List<Integer> ids = this.followService.getFollowers(userId, EntityType.ENTITY_USER, 10);
        model.addAttribute("followers", this.getUsersInfo(userId, ids));
        model.addAttribute("followerCount", followService.getFollowerCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.getById(userId));

        return "followers";
    }

    /**
     * 某人的关注列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = {"/user/{userId}/followees"}, method = RequestMethod.GET)
    public String showFollowees(@PathVariable("userId") int userId, Model model) {
        /*User localUser = this.hostHolder.getUser();
        if (localUser == null) {
            return "login";
        }*/
        List<Integer> ids = this.followService.getFollowees(userId, EntityType.ENTITY_USER, 10);
        model.addAttribute("followees", this.getUsersInfo(userId, ids));
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.getById(userId));

        return "followees";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> ids) {
        List<ViewObject> userInfos = new ArrayList<ViewObject>();
        for (Integer item : ids) {
            User curUser = this.userService.getById(item);
            if (curUser == null) {
                continue;
            }
            ViewObject vo = new ViewObject();

            vo.set("user", curUser);
            vo.set("commentCount", this.commentService.getUserCommentCount(item));
            vo.set("followerCount", this.followService.getFollowerCount(item, EntityType.ENTITY_USER));
            vo.set("followeeCount", this.followService.getFolloweeCount(item, EntityType.ENTITY_USER));
            vo.set("followed", this.followService.isFollower(localUserId, item, EntityType.ENTITY_USER));

            userInfos.add(vo);
        }
        return userInfos;
    }
}