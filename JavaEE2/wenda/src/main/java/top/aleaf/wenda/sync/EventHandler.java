/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventHandler
 * Author:   郭新晔
 * Date:     2019/1/22 0022 22:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.sync;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);

    List<EventType> getSupportTypes();
}