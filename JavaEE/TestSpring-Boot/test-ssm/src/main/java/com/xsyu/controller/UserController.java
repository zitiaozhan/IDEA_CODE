/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserController
 * Author:   郭新晔
 * Date:     2018/6/17 0017 13:43
 * Description: User控制器---使用Restful风格
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.controller;

import com.xsyu.mapper.UserMapper;
import com.xsyu.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br> 
 * 〈User控制器---使用Restful风格〉
 *
 * @author 郭新晔
 * @create 2018/6/17 0017
 * @since 1.0.0
 *
 * RESTFUL中的HTTP Method
 * POST：新增资源
 * PUT：修改资源，客户端提供完整的资源属性
 * GET：查询资源
 * PATCH：更新资源，客户端提供仅需更改的属性
 * DELETE：删除资源
 * HEAD：交换HTTP头信息
 * OPTIONS：获取URI所支持的方法。如针对跨域的预检
 *
 */
@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    //SpringMVC之前的复杂写法@RequestMapping(value="/user/{id}",method = RequestMethod.GET)
    //@GetMapping
    //@PostMapping
    //@DeleteMapping
    //@PutMapping
    @GetMapping("/user/{uid}")
    @ResponseBody
    public User getUser(@PathVariable("uid") Integer uid){
        return this.userMapper.getUser(uid);
    }
}