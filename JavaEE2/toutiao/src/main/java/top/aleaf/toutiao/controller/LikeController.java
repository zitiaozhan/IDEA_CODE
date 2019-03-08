/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LikeController
 * Author:   郭新晔
 * Date:     2018/12/14 0014 21:25
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.aleaf.toutiao.model.EntityType;
import top.aleaf.toutiao.model.HostHolder;
import top.aleaf.toutiao.service.LikeService;
import top.aleaf.toutiao.service.NewsService;
import top.aleaf.toutiao.sync.EventModel;
import top.aleaf.toutiao.sync.EventProducer;
import top.aleaf.toutiao.sync.EventType;
import top.aleaf.toutiao.utils.ToutiaoUtil;

/**
 * 〈〉
 *
 * @create 2018/12/14 0014
 */
@Controller
public class LikeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeController.class);
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private NewsService newsService;
    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String likeEntity(@RequestParam("newsId") int newsId) {
        try {
            if (this.hostHolder.getUser() == null) {
                LOGGER.error("用户未登录！");
                return ToutiaoUtil.getJSONString(1, "用户未登录！");
            }
            int userId = this.hostHolder.getUser().getId();
            long likeCount = this.likeService.like(userId, EntityType.NEWS, newsId);
            this.newsService.setLikeCount(newsId, (int) likeCount);

            //点击喜欢后形成事件
            this.eventProducer.fireEvent(new EventModel(EventType.LIKE)
                    .setActor(userId).setEntityId(newsId)
                    .setEntityOwnerId(this.newsService.getUserById(newsId).getId())
                    .setEntityType(EntityType.NEWS));

            return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            LOGGER.error("点赞发生异常！" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "点赞发生异常！");
        }
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislikeEntity(@RequestParam("newsId") int newsId) {
        try {
            if (this.hostHolder.getUser() == null) {
                return ToutiaoUtil.getJSONString(1, "用户未登录！");
            }
            int userId = this.hostHolder.getUser().getId();
            long likeCount = this.likeService.dislike(userId, EntityType.NEWS, newsId);
            this.newsService.setLikeCount(newsId, (int) likeCount);
            return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            return ToutiaoUtil.getJSONString(1, "点踩发生异常！" + e.getMessage());
        }
    }
}