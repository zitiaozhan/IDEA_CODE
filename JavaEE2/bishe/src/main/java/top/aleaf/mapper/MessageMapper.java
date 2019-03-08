/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserMapper
 * Author:   郭新晔
 * Date:     2019/2/11 0011 16:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.aleaf.model.Message;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Repository
public interface MessageMapper extends Mapper<Message> {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id,to_id,content,created_date,has_read,conversation_id,status ";
    String INSERT_VALUES = " #{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId},#{status} ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Select({"SELECT ", INSERT_FIELDS, ",COUNT(id) AS id FROM ( " +
            "SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE STATUS!=1 ORDER BY id DESC " +
            ") AS HELP " +
            "WHERE from_id=#{userId} OR to_id=#{userId} GROUP BY conversation_id " +
            "order by created_date desc "})
    public List<Message> selectConversationList(@Param("userId") int userId);

    @Update({"update ", TABLE_NAME, " set has_read=#{hasRead} " +
            "where status!=1 and conversation_id=#{conversationId} and to_id=#{toId} "})
    public void updateHasRead(@Param("conversationId") String conversationId,
                              @Param("hasRead") int hasRead,
                              @Param("toId") int toId);

    public List<Message> selectSearchByName(@Param("ids") List<Integer> ids, @Param("myId") int myId);
}