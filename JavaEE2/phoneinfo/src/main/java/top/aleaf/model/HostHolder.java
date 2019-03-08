/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HostHolder
 * Author:   郭新晔
 * Date:     2018/12/21 0021 19:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈〉
 *
 * @create 2018/12/21 0021
 */
@Component
public class HostHolder {
    private static ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<>();

    public Map<String, Object> getSpace() {
        return mapThreadLocal.get();
    }

    public Object getSpaceValue(String key) {
        return getSpace().get(key);
    }

    public void addSpace(String key, Object value) {
        Map<String, Object> space = getSpace();
        space.put(key, value);
        mapThreadLocal.set(space);
    }

    public void setSpace(Map<String, Object> map) {
        mapThreadLocal.set(map);
    }

    public void clear() {
        mapThreadLocal.remove();
    }
}