/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ViewObject
 * Author:   郭新晔
 * Date:     2019/1/18 0018 16:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 * @author 郭新晔
 * @create 2019/1/18 0018
 */
public class ViewObject {
    private Map<String,Object> map=new HashMap();

    public Object get(String key){
        return map.get(key);
    }

    public void set(String key,Object value){
        map.put(key,value);
    }
}