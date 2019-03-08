/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventProducer
 * Author:   郭新晔
 * Date:     2019/1/9 0009 20:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.sync;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.utils.JedisAdapter;
import top.aleaf.toutiao.utils.RedisKeyUtil;

/**
 * 〈〉
 *
 * @create 2019/1/9 0009
 */
@Service
public class EventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 将事件加入到队列
     * @param model
     * @return
     */
    public boolean fireEvent(EventModel model) {
        try {
            String value = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("事件加入队列失败！" + e.getMessage());
            return false;
        }
    }
}