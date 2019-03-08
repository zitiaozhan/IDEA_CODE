/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HomeController
 * Author:   郭新晔
 * Date:     2018/11/22 0022 23:08
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.aleaf.toutiao.model.EntityType;
import top.aleaf.toutiao.model.HostHolder;
import top.aleaf.toutiao.model.News;
import top.aleaf.toutiao.model.ViewObject;
import top.aleaf.toutiao.service.LikeService;
import top.aleaf.toutiao.service.NewsService;
import top.aleaf.toutiao.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/11/22 0022
 */
@Controller
public class HomeController {
    //日志  切面处理
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<ViewObject> vos = new ArrayList<>();
        List<News> newsList = this.newsService.getLatestNews(userId, offset, limit);
        int localUserId = this.hostHolder.getUser() == null ? 0 : this.hostHolder.getUser().getId();
        for (News item : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", item);
            vo.set("user", userService.getUser(item.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.NEWS, item.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", this.getNews(0, 0, 10));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(@PathVariable(value = "userId") int userId, Model model) {
        model.addAttribute("vos", this.getNews(userId, 0, 10));
        return "home";
    }
}