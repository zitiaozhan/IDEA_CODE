/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: KeysUtil
 * Author:   郭新晔
 * Date:     2019/1/22 0022 15:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.util;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
public class KeysUtil {
    public static final String SPLIT = ":";
    public static final String LIKE = "LIKE";
    public static final String EVENTQUEUE = "EVENT_QUEUE";
    public static final String BIZ_FOLLOWER = "FOLLOWER";
    public static final String BIZ_FOLLOWEE = "FOLLOWEE";

    private static final String BIZ_TIMELINE = "TIMELINE";

    public static String getLikeKey(int entityId, String entityType) {
        return LIKE + SPLIT + String.valueOf(entityId) + SPLIT + entityType;
    }

    public static String getEVENTQUEUE() {
        return EVENTQUEUE;
    }

    // 某个实体的粉丝key,粉丝
    public static String getFollowerKey(int entityId, String entityType) {
        return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + String.valueOf(entityId);
    }

        // 每个用户对某类实体的关注key，我的关注
    public static String getFolloweeKey(int userId, String entityType) {
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + entityType;
    }

    public static String getTimelineKey(int userId) {
        return BIZ_TIMELINE + SPLIT + String.valueOf(userId);
    }
}