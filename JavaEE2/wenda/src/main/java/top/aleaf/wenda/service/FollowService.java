/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FollowService
 * Author:   郭新晔
 * Date:     2019/1/23 0023 22:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import top.aleaf.wenda.model.EntityType;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.KeysUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 〈〉
 *
 * @create 2019/1/23 0023
 */
@Service
public class FollowService {
    @Autowired
    private JedisAdapter adapter;

    public boolean follow(int userId, int entityId, EntityType entityType) {
        String followerKey = KeysUtil.getFollowerKey(entityId, entityType.getValue());
        String followeeKey = KeysUtil.getFolloweeKey(userId, entityType.getValue());

        Date date = new Date();

        Jedis jedis = this.adapter.getJedis();
        Transaction tx = this.adapter.multi(jedis);
        //实体的粉丝增加当前用户
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        //某用户的关注列表加入某实体
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> objectList = this.adapter.exec(tx, jedis);
        return objectList.size() == 2 && (long) objectList.get(0) > 0 && (long) objectList.get(1) > 0;
    }

    public boolean unfollow(int userId, int entityId, EntityType entityType) {
        String followerKey = KeysUtil.getFollowerKey(entityId, entityType.getValue());
        String followeeKey = KeysUtil.getFolloweeKey(userId, entityType.getValue());

        Jedis jedis = this.adapter.getJedis();
        Transaction tx = this.adapter.multi(jedis);
        //实体的粉丝移除当前用户
        tx.zrem(followerKey, String.valueOf(userId));
        //某用户的关注列表移除某实体
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> objectList = this.adapter.exec(tx, jedis);
        return objectList.size() == 2 && (long) objectList.get(0) > 0 && (long) objectList.get(1) > 0;
    }

    //粉丝
    public List<Integer> getFollowers(int entityId, EntityType entityType, int count) {
        String followerKey = KeysUtil.getFollowerKey(entityId, entityType.getValue());
        return convertSet2List(this.adapter.zrevrange(followerKey, 0, count));
    }

    //粉丝
    public List<Integer> getFollowers(int entityId, EntityType entityType, int offset, int count) {
        String followerKey = KeysUtil.getFollowerKey(entityId, entityType.getValue());
        return convertSet2List(this.adapter.zrevrange(followerKey, offset, count));
    }

    //我的关注
    public List<Integer> getFollowees(int userId, EntityType entityType, int count) {
        String followeeKey = KeysUtil.getFolloweeKey(userId, entityType.getValue());
        return convertSet2List(this.adapter.zrevrange(followeeKey, 0, count));
    }

    //我的关注
    public List<Integer> getFollowees(int userId, EntityType entityType, int offset, int count) {
        String followeeKey = KeysUtil.getFolloweeKey(userId, entityType.getValue());
        return convertSet2List(this.adapter.zrevrange(followeeKey, offset, count));
    }

    //粉丝数量
    public long getFollowerCount(int entityid, EntityType entityType) {
        String followerKey = KeysUtil.getFollowerKey(entityid, entityType.getValue());
        return this.adapter.zcard(followerKey);
    }

    //关注数量
    public long getFolloweeCount(int userId, EntityType entityType) {
        String followeeKey = KeysUtil.getFolloweeKey(userId, entityType.getValue());
        return this.adapter.zcard(followeeKey);
    }

    public boolean isFollower(int userId, int entityId, EntityType entityType) {
        String followerKey = KeysUtil.getFollowerKey(entityId, entityType.getValue());
        return this.adapter.zscore(followerKey, userId) != null;
    }

    public List<Integer> convertSet2List(Set<String> set) {
        List<Integer> list = new ArrayList<>();
        for (String item : set) {
            list.add(Integer.parseInt(item));
        }
        return list;
    }
}