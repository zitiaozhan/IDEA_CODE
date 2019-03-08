/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HomeController
 * Author:   郭新晔
 * Date:     2019/1/18 0018 16:14
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.aleaf.wenda.model.*;
import top.aleaf.wenda.service.CommentService;
import top.aleaf.wenda.service.FollowService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/18 0018
 */
@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FollowService followService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    private List<ViewObject> getViewObjects(Question question) {
        List<ViewObject> vos = new ArrayList<>();
        List<Question> questionList = this.questionService.getAll(question);
        for (Question item : questionList) {
            ViewObject vo = new ViewObject();
            int commentCount = commentService.getCommentCount(item.getId(), EntityType.ENTITY_QUESTION.getValue());
            if (item.getCommentCount() != commentCount) {
                item.setCommentCount(commentCount);
                this.questionService.setCommentCount(item.getId(), commentCount);
            }
            long followCount = this.followService.getFollowerCount(item.getId(), EntityType.ENTITY_QUESTION);

            User localUser = this.hostHolder.getUser();
            boolean followed = false;
            boolean isOwner = false;
            if (localUser != null) {
                if (localUser.getId().equals(item.getUserId())) {
                    isOwner = true;
                } else {
                    followed = this.followService.isFollower(localUser.getId(), item.getId(), EntityType.ENTITY_QUESTION);
                }
            }
            vo.set("isOwner", isOwner);
            vo.set("followed", followed);

            vo.set("question", item);
            vo.set("followCount", followCount);
            vo.set("user", this.userService.getById(item.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        List<ViewObject> vos = getViewObjects(new Question());
        model.addAttribute("vos", vos);
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        User me = this.hostHolder.getUser();

        ViewObject profileUser = new ViewObject();
        User user = this.userService.getById(userId);
        profileUser.set("user", user);

        boolean isOwner = me == null || !me.getId().equals(userId) ? false : true;
        profileUser.set("isOwner", isOwner);

        long followerCount = this.followService.getFollowerCount(userId, EntityType.ENTITY_USER);
        profileUser.set("followerCount", followerCount);

        long followeeCount = this.followService.getFolloweeCount(userId, EntityType.ENTITY_USER);
        profileUser.set("followeeCount", followeeCount);

        int commentCount = this.commentService.getUserCommentCount(userId);
        profileUser.set("commentCount", commentCount);

        boolean followed = me == null ? false : this.followService.isFollower(me.getId(), userId, EntityType.ENTITY_USER);
        profileUser.set("followed", followed);
        model.addAttribute("profileUser", profileUser);

        List<ViewObject> vos = getViewObjects(new Question().setUserId(userId));
        model.addAttribute("vos", vos);
        return "profile";
    }
}