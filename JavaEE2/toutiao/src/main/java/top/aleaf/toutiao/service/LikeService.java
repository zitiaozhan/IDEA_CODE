/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LikeService
 * Author:   郭新晔
 * Date:     2018/12/14 0014 21:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.utils.JedisAdapter;
import top.aleaf.toutiao.utils.RedisKeyUtil;

/**
 * 〈〉
 *
 * @create 2018/12/14 0014
 */
@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 判断用户是否喜欢，喜欢返回1，不喜欢返回-1，否则返回0
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (this.jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        if (this.jedisAdapter.sismember(dislikeKey, String.valueOf(userId))) {
            return -1;
        }
        return 0;
    }

    /**
     * 点击喜欢时，将用户添加到喜欢集合中
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        this.jedisAdapter.sadd(likeKey, String.valueOf(userId));

        //从不喜欢的集合中移除此用户
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        this.jedisAdapter.srem(dislikeKey, String.valueOf(userId));

        return this.jedisAdapter.scard(likeKey);
    }
    /**
     * 点击不喜欢时，将用户添加到不喜欢集合中
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long dislike(int userId, int entityType, int entityId) {
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        this.jedisAdapter.sadd(dislikeKey, String.valueOf(userId));

        //从喜欢的集合中移除此用户
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        this.jedisAdapter.srem(likeKey, String.valueOf(userId));
        //仍然返回喜欢的人数
        return this.jedisAdapter.scard(likeKey);
    }
}