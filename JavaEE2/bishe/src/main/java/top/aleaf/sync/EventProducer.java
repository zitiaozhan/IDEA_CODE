/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventProducer
 * Author:   郭新晔
 * Date:     2019/1/22 0022 22:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.sync;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.utils.JedisAdapter;
import top.aleaf.utils.KeysUtil;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
@Service
public class EventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String model = JSON.toJSONString(eventModel);
            this.jedisAdapter.lpush(KeysUtil.EVENTQUEUE, model);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}