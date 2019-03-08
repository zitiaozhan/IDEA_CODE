/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: QuestionController
 * Author:   郭新晔
 * Date:     2019/1/19 0019 18:26
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
import top.aleaf.wenda.service.*;
import top.aleaf.wenda.util.WendaUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/19 0019
 */
@Controller
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowService followService;
    @Autowired
    private SensitiveFilterService sensitiveFilterService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam(value = "title") String title,
                              @RequestParam(value = "content") String content) {
        try {
            //过滤敏感词汇
            title = sensitiveFilterService.filterSensitiveWord(title);
            content = sensitiveFilterService.filterSensitiveWord(content);
            //转义HTML字符
            //title = HtmlUtils.htmlEscape(title);
            //content = HtmlUtils.htmlEscape(content);
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            this.questionService.save(question);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            LOGGER.error("问题发布异常：" + e.getMessage());
            return WendaUtil.getJSONString(1, "问题发布异常");
        }
    }

    @RequestMapping(path = {"/question/{qid}"})
    public String showDetail(@PathVariable(value = "qid") int qid,
                             Model model) {
        Question question = this.questionService.getById(qid);
        List<Comment> commentList = this.commentService.getAll(
                new Comment().setEntityId(question.getId()).setEntityType(EntityType.ENTITY_QUESTION.getValue()));
        List<ViewObject> vos = new ArrayList<>();
        User user = hostHolder.getUser();
        if (user == null) {
            user = this.userService.getById(WendaUtil.ANONYMOUSID);
        }
        for (Comment item : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", item);
            vo.set("likeCount", this.likeService.likeCount(item.getId(), EntityType.ENTITY_COMMENT.getValue()));
            int liked = user != null && this.likeService.isLike(user.getId(), item.getId(), EntityType.ENTITY_COMMENT.getValue()) ? 1 : 0;
            vo.set("liked", liked);
            vo.set("user", this.userService.getById(item.getUserId()));
            vos.add(vo);
        }

        List<Integer> ids = this.followService.getFollowers(question.getId(), EntityType.ENTITY_QUESTION, 10);
        List<ViewObject> followUsers = getUsersInfo(ids);

        model.addAttribute("followed", this.followService.isFollower(user.getId(), question.getId(), EntityType.ENTITY_QUESTION));
        model.addAttribute("followUsers", followUsers);
        model.addAttribute("question", question);
        model.addAttribute("qUser", this.userService.getById(question.getUserId()));
        model.addAttribute("comments", vos);
        return "detail";
    }

    private List<ViewObject> getUsersInfo(List<Integer> ids) {
        List<ViewObject> userInfos = new ArrayList<ViewObject>();
        for (Integer item : ids) {
            User curUser = this.userService.getById(item);
            if (curUser == null) {
                continue;
            }
            ViewObject vo = new ViewObject();

            vo.set("id", curUser.getId());
            vo.set("name", curUser.getName());
            vo.set("headUrl", curUser.getHeadUrl());

            userInfos.add(vo);
        }
        return userInfos;
    }

}