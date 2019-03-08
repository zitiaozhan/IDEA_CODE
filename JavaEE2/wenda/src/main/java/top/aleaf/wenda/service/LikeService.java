/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: LikeService
 * Author:   郭新晔
 * Date:     2019/1/22 0022 15:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.KeysUtil;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    public long like(int userId, int entityId, String entityType) {
        String keyStr = KeysUtil.getLikeKey(entityId, entityType);
        String value = String.valueOf(userId);
        if (jedisAdapter.sismember(keyStr, value)) {
            jedisAdapter.srem(keyStr, value);
        } else {
            jedisAdapter.sadd(keyStr, value);
        }

        return jedisAdapter.scard(keyStr);
    }

    public long likeCount(int entityId, String entityType) {
        String keyStr = KeysUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(keyStr);
    }

    public boolean isLike(int userId, int entityId, String entityType) {
        String keyStr = KeysUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.sismember(keyStr, String.valueOf(userId));
    }
}