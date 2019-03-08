/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventConsumer
 * Author:   郭新晔
 * Date:     2019/1/22 0022 22:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.sync;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.util.JedisAdapter;
import top.aleaf.wenda.util.KeysUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    private ApplicationContext context;
    private Map<EventType, List<EventHandler>> eventTypeConfig = new HashMap<>();
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> allHandler = context.getBeansOfType(EventHandler.class);
        if (allHandler != null) {
            for (Map.Entry<String, EventHandler> item : allHandler.entrySet()) {
                List<EventType> eventTypes = item.getValue().getSupportTypes();
                for (EventType type : eventTypes) {
                    if (!eventTypeConfig.containsKey(type)) {
                        this.eventTypeConfig.put(type, new ArrayList<>());
                    }
                    this.eventTypeConfig.get(type).add(item.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<String> models = jedisAdapter.brpop(0, KeysUtil.getEVENTQUEUE());
                    for (String item : models) {
                        if (item.equals(KeysUtil.getEVENTQUEUE())) {
                            continue;
                        }

                        EventModel eventModel = JSONObject.parseObject(item, EventModel.class);
                        if (!eventTypeConfig.containsKey(eventModel.getEventType())) {
                            LOGGER.warn("无法识别的事件");
                            continue;
                        }
                        List<EventHandler> handlers = eventTypeConfig.get(eventModel.getEventType());
                        for (EventHandler handler : handlers) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}