/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MessageDAO
 * Author:   郭新晔
 * Date:     2018/12/11 0011 21:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.toutiao.model.Message;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/11 0011
 */
@Repository
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id,to_id,content,created_date,has_read,conversation_id,status ";
    String INSERT_VALUES = " #{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId},#{status} ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    public int addMessage(Message message);

    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    public void updateStatus(@Param("messageId") int messageId, @Param("status") int status);

    @Update({"update ", TABLE_NAME, " set has_read=1 where to_id=#{userId} and conversation_id=#{conversationId}"})
    public void updateHasRead(@Param("userId")int userId, @Param("conversationId")String conversationId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} and status=0 order by id desc limit #{offset},#{limit}"})
    public List<Message> selectConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"SELECT ",INSERT_FIELDS,",COUNT(id) AS id FROM " +
            "(" +
                "SELECT * FROM ",
                TABLE_NAME,
                "WHERE from_id=#{userId} OR to_id=#{userId} " +
                "AND STATUS=0 " +
                "ORDER BY id DESC " +
            ") AS tmp " +
            "GROUP BY conversation_id " +
            "ORDER BY created_date DESC " +
            "LIMIT #{offset},#{limit}"})
    public List<Message> selectConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"SELECT COUNT(has_read) AS COUNT FROM ",
            TABLE_NAME,
            "WHERE 1=1 " +
            "AND STATUS=0 " +
            "AND has_read=0 " +
            "AND to_id=#{userId} " +
            "AND conversation_id=#{conversationId}"})
    public int selectUnreadByConversationId(@Param("userId")int userId, @Param("conversationId")String conversationId);
}