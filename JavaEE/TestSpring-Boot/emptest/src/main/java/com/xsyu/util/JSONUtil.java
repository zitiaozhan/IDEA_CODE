/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JSONUtil
 * Author:   郭新晔
 * Date:     2018/6/20 0020 19:52
 * Description: json工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.util;

import com.alibaba.fastjson.serializer.PropertyFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈json工具〉
 *
 * @author 郭新晔
 * @create 2018/6/20 0020
 * @since 1.0.0
 */
public class JSONUtil {
    public static void printString(HttpServletResponse response, String string){
        response.setCharacterEncoding("utf-8");

        try(PrintWriter writer=response.getWriter()) {
            writer.print(string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static PropertyFilter filteProperty(final String... params){
        PropertyFilter pf=new PropertyFilter() {
            @Override
            public boolean apply(Object o, String proName, Object o1) {
                for(int i=0;i<params.length;i++){
                    if(proName.equals(params[i])){
                        return false;
                    }
                }
                return true;
            }
        };
        return pf;
    }
}