/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FeedHandler
 * Author:   郭新晔
 * Date:     2019/1/25 0025 16:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.sync.handler;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.aleaf.wenda.model.EntityType;
import top.aleaf.wenda.model.Feed;
import top.aleaf.wenda.model.Question;
import top.aleaf.wenda.model.User;
import top.aleaf.wenda.service.FeedService;
import top.aleaf.wenda.service.FollowService;
import top.aleaf.wenda.service.QuestionService;
import top.aleaf.wenda.service.UserService;
import top.aleaf.wenda.sync.EventHandler;
import top.aleaf.wenda.sync.EventModel;
import top.aleaf.wenda.sync.EventType;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.KeysUtil;

import java.util.*;

/**
 * 〈〉
 *
 * @create 2019/1/25 0025
 */
@Component
public class FeedHandler implements EventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeHandler.class);

    @Autowired
    private FeedService feedService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void doHandle(EventModel eventModel) {
        try {
            Feed feed = new Feed();
            feed.setCreatedDate(new Date());
            feed.setType(eventModel.getEventType().getValue());
            feed.setUserId(eventModel.getActorId());
            String data = this.builderFeedData(eventModel);
            if (data == null) {
                return;
            }
            feed.setData(data);

            this.feedService.addFeed(feed);

            //推送给每一个粉丝
            List<Integer> followers = this.followService.getFollowers(eventModel.getActorId(),
                    EntityType.ENTITY_USER, Integer.MAX_VALUE);
            followers.add(0);
            for (int item : followers) {
                this.jedisAdapter.lpush(KeysUtil.getTimelineKey(item),
                        String.valueOf(feed.getId()));
            }
        } catch (Exception e) {
            LOGGER.error("数据拉取错误");
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }

    private String builderFeedData(EventModel eventModel) {
        Map<String, String> map = new HashMap<>();

        User user = this.userService.getById(eventModel.getActorId());
        if (user == null) {
            return null;
        }
        map.put("userId", String.valueOf(user.getId()));
        map.put("userName", user.getName());
        map.put("userHead", user.getHeadUrl());
        if (eventModel.getEventType() == EventType.COMMENT || eventModel.getEventType() == EventType.FOLLOW) {
            if (eventModel.getEntityType().equals(EntityType.ENTITY_QUESTION.getValue())) {
                Question question = this.questionService.getById(eventModel.getEntityId());
                if (question == null) {
                    return null;
                }
                map.put("questionId", String.valueOf(question.getId()));
                map.put("questionTitle", question.getTitle());
                return JSON.toJSONString(map);
            }
        }
        return null;
    }
}