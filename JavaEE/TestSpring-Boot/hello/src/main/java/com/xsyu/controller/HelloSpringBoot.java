/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: HelloSpringBoot
 * Author:   郭新晔
 * Date:     2018/6/17 0017 11:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈SpringBoot测试Controller〉
 * @RestController的意思就是controller里面的方法都以json格式输出，不用再配置jackjson
 *
 * @author 郭新晔
 * @create 2018/6/16 0016
 * @since 0.0.1
 */
@RestController
public class HelloSpringBoot {
    //@Responsebody 注解表示该方法的返回的结果直接写入
    //HTTP 响应正文（ResponseBody）中，一般在异步获取数据时使用
    @ResponseBody
    @RequestMapping(value="/hello")
    public String hello(){
        return "hello SpringBoot";
    }
}