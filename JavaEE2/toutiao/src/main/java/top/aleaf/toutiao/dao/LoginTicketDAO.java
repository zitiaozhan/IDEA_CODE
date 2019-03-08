/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LoginTicketDAO
 * Author:   郭新晔
 * Date:     2018/11/28 0028 19:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.toutiao.model.LoginTicket;

/**
 * 〈〉
 *
 * @create 2018/11/28 0028
 */
@Repository
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id,expired,ticket,status ";
    String INSERT_VALUES = " #{userId},#{expired},#{ticket},#{status} ";
    String SELECT_FIELDS = " id,user_id,expired,ticket,status ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", " values(", INSERT_VALUES, ")"})
    public int addLoginTicket(LoginTicket ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    public void updateLoginTicket(@Param("ticket") String ticket, @Param("status") int status);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    public LoginTicket selectByTicket(String ticket);
}