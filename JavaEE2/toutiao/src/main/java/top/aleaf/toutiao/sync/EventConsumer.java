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

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import top.aleaf.toutiao.utils.JedisAdapter;
import top.aleaf.toutiao.utils.RedisKeyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/9 0009
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    //注册每个事件类型的处理器链
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //找到所有的在Spring框架下标注的实现指定类或接口的类的集合
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            //初始化事件的处理器链
            //一个事件可能有多个处理器都进行响应，例如，点赞后进行通知、分数增加等等
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType item : eventTypes) {
                    if (!config.containsKey(item)) {
                        config.put(item, new ArrayList<>());
                    }
                    config.get(item).add(entry.getValue());
                }
            }
        }

        //不要显式的创建线程，应使用线程池
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    //BRPOP 是redis列表的阻塞式(blocking)弹出原语。
                    //当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。
                    //返回值：
                    //假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
                    //反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String item : events) {
                        if (item.equals(key)) {
                            continue;
                        }

                        EventModel model = JSON.parseObject(item, EventModel.class);

                        if (!config.containsKey(model.getEventType())) {
                            LOGGER.error("不能识别的事件！");
                        } else {
                            //取出事件类型对应的若干处理器进行处理
                            List<EventHandler> eventHandlers = config.get(model.getEventType());
                            for (EventHandler handler : eventHandlers) {
                                handler.doHandle(model);
                            }
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}