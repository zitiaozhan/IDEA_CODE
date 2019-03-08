/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserDAO
 * Author:   郭新晔
 * Date:     2018/11/20 0020 16:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.toutiao.model.User;

/**
 * 〈〉
 *
 * @create 2018/11/20 0020
 */
@Repository
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name,password,salt,head_url ";
    String INSERT_VALUES = " #{name},#{password},#{salt},#{headUrl} ";
    String SELECT_FIELDS = " id,name,password,salt,head_url,email ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}