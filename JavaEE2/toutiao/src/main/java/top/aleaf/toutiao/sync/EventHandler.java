/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventHandler
 * Author:   郭新晔
 * Date:     2019/1/9 0009 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.sync;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/9 0009
 */
public interface EventHandler {
    //该处理器所要做的具体处理
    void doHandle(EventModel model);
    //EventHandler关注的对象，
    //例如：新闻事件处理器可以处理新闻发布提醒、新闻评论、新闻排名等有关新闻对的一切操作
    List<EventType> getSupportEventTypes();
}