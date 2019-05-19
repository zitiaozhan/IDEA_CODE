/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserMapper
 * Author:   郭新晔
 * Date:     2018/6/17 0017 13:49
 * Description: User的映射接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.mapper;

import com.xsyu.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 
 * 〈User的映射接口〉
 *
 * @author 郭新晔
 * @create 2018/6/17 0017
 * @since 1.0.0
 */
//@Mapper作用:告诉MyBatis这是一个操作数据库的Mapper
@Mapper
@Service
public interface UserMapper {
    @Select("select * from user where uid=#{uid}")
    public User getUser(Integer uid);
}