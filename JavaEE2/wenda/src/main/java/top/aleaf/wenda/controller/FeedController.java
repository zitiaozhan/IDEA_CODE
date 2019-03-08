/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeedController
 * Author:   郭新晔
 * Date:     2019/1/25 0025 17:59
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.aleaf.wenda.model.EntityType;
import top.aleaf.wenda.model.Feed;
import top.aleaf.wenda.model.HostHolder;
import top.aleaf.wenda.service.FeedService;
import top.aleaf.wenda.service.FollowService;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.KeysUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 * @create 2019/1/25 0025
 */
@Controller
public class FeedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedController.class);
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisAdapter jedisAdapter;
    @Autowired
    private HostHolder hostHolder;

    /**
     * 数据库拉取数据
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/pullfeeds"}, method = {RequestMethod.GET})
    public String getPullFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if (localUserId != 0) {
            // 关注的人
            followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

    /**
     * 推送数据
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/pushfeeds"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String getPushFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<String> feedIds = jedisAdapter.lrange(KeysUtil.getTimelineKey(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<Feed>();
        for (String feedId : feedIds) {
            Feed feed = feedService.getById(Integer.parseInt(feedId));
            if (feed != null) {
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}