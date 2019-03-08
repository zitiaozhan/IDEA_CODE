/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ViewObject
 * Author:   郭新晔
 * Date:     2018/11/24 0024 15:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2018/11/24 0024
 */
@Component
public class ViewObject {
    private final Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}