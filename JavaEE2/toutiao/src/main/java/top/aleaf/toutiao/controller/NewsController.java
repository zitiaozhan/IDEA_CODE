/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NewsController
 * Author:   郭新晔
 * Date:     2018/12/4 0004 20:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.aleaf.toutiao.model.*;
import top.aleaf.toutiao.service.*;
import top.aleaf.toutiao.utils.ToutiaoUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/4 0004
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LikeService likeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    /**
     * 图片上传
     *
     * @param file POST方式得到的文件
     * @return JSON字符串
     */
    @RequestMapping(path = {"/uploadImage/"}, method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
//            String imageUrl = this.newsService.saveImage(file);
            String imageUrl = this.qiniuService.saveImage(file);
            if (imageUrl == null) {
                //失败code为1
                return ToutiaoUtil.getJSONString(1, "图片上传失败！");
            }
            //成功code为0
            LOGGER.error("图片上传成功！");
            return ToutiaoUtil.getJSONString(0, imageUrl);
        } catch (Exception e) {
            LOGGER.error("图片上传失败！");
            return ToutiaoUtil.getJSONString(1, "图片上传失败！");
        }
    }

    /**
     * 查看图片，通过链接查看(当通过本地文件上传实现时才需要)
     *
     * @param imageName 获得文件名才能从服务器找到文件
     * @param response
     */
    @RequestMapping(path = {"/image"}, method = RequestMethod.GET)
    @ResponseBody
    public void getImage(@RequestParam(value = "name") String imageName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.UPLOAD_IMAGE_PATH + imageName)), response.getOutputStream());
            LOGGER.error("图片加载成功！");
        } catch (Exception e) {
            LOGGER.error("图片加载失败！" + e.getMessage());
        }
    }

    /**
     * 新增咨询
     *
     * @param image 咨询图片链接
     * @param title 咨询标题
     * @param link  咨询详情链接
     * @return 咨询发布结果
     */
    @RequestMapping(path = {"/user/addNews/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image, @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        if (!ToutiaoUtil.judgeURL(link)) {
            LOGGER.error("ID为：[" + hostHolder.getUser().getId() + "]的用户发帖失败，标题为：[" + title + "]");
            LOGGER.error("错误原因：填写链接不合法！");
            return ToutiaoUtil.getJSONString(1, "用户发帖失败!");
        }
        try {
            News news = new News();
            news.setUserId(hostHolder.getUser().getId());
            if (hostHolder.getUser() == null) {
                //假设id为3的用户表示匿名用户身份
                news.setUserId(3);
            }
            news.setCreateDate(new Date());
            news.setImage(image);
            news.setLink(link);
            news.setTitle(title);
            this.newsService.addNews(news);
            LOGGER.info("用户发帖成功，ID为：[" + hostHolder.getUser().getId() + "]，标题为：[" + title + "]");
        } catch (Exception e) {
            LOGGER.error("ID为：[" + hostHolder.getUser().getId() + "]的用户发帖失败，标题为：[" + title + "]");
            LOGGER.error("错误原因：" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "用户发帖失败!");
        }
        return ToutiaoUtil.getJSONString(0, "发布成功！");
    }

    /**
     * 查看咨询详情
     *
     * @param newsId 咨询ID
     * @param model
     * @return Velocity渲染的页面
     */
    @RequestMapping(path = {"/news/{newsId}", "/news/{newsId}/"}, method = RequestMethod.GET)
    public String newsDetail(@PathVariable(value = "newsId") int newsId, Model model) {
        News news = this.newsService.getNewsById(newsId);
        if (news == null) {
            LOGGER.error("加载咨询详情出错，该咨询不存在！");
            return "/index";
        }
        User owner = this.userService.getUser(news.getUserId());
        int commentCount = this.commentService.getCommentCountByEntity(news.getId(), EntityType.NEWS);
        if (news.getCommentCount() != commentCount) {
            news.setCommentCount(commentCount);
            this.newsService.setCommentCount(news.getId(), commentCount);
        }
        int localUserId = this.hostHolder.getUser() == null ? 0 : this.hostHolder.getUser().getId();
        List<ViewObject> vos = new ArrayList<>();
        List<Comment> commentList = this.commentService.getCommentByEntity(news.getId(), EntityType.NEWS);
        for (Comment item : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("user", this.userService.getUser(item.getUserId()));
            vo.set("comment", item);
            vos.add(vo);
        }

        if (localUserId != 0) {
            model.addAttribute("like", likeService.getLikeStatus(localUserId, EntityType.NEWS, newsId));
        } else {
            model.addAttribute("like", 0);
        }

        model.addAttribute("news", news);
        model.addAttribute("owner", owner);
        model.addAttribute("vos", vos);
        return "detail";
    }

    @RequestMapping(path = {"/addComment"}, method = RequestMethod.POST)
    public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content) {
        try {
            if (content == null || "".equals(content.trim())) {
                LOGGER.error("新增评论失败！评论不合法");
                return "redirect:/news/" + newsId;
            }
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUserId(this.hostHolder.getUser().getId());
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.NEWS);
            comment.setEntityId(newsId);
            this.commentService.addComment(comment);
            //更新评论数量
            News news = this.newsService.getNewsById(newsId);
            int commentCount = this.commentService.getCommentCountByEntity(news.getId(), EntityType.NEWS);
            if (news.getCommentCount() != commentCount) {
                this.newsService.setCommentCount(news.getId(), commentCount);
            }
        } catch (Exception e) {
            LOGGER.error("新增评论失败！" + e.getMessage());
        }
        return "redirect:/news/" + newsId;
    }

    @RequestMapping(path = {"/deleteComment/{newsId}/{commentId}"}, method = RequestMethod.GET)
    public String deleteComment(@PathVariable(value = "newsId") int newsId, @PathVariable(value = "commentId") int commentId) {
        try {
            this.commentService.deleteComment(commentId);
        } catch (Exception e) {
            LOGGER.error("删除评论失败！" + e.getMessage());
        }
        return "redirect:/news/" + newsId;
    }
}