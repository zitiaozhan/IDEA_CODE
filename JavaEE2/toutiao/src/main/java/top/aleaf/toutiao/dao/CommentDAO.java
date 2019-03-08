/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CommentDAO
 * Author:   郭新晔
 * Date:     2018/12/8 0008 21:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.toutiao.model.Comment;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/8 0008
 */
@Repository
@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " content,created_date,entity_id,entity_type,user_id,status ";
    String INSERT_VALUES = " #{content},#{createdDate},#{entityId},#{entityType},#{userId},#{status} ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    public int addComment(Comment comment);

    @Update({"update ", TABLE_NAME, " set status=#{status} where 1=1 and id=#{id}"})
    public void updateStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and status=0 and entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    public List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and status=0 and entity_id=#{entityId} and entity_type=#{entityType} order by id desc limit #{offset},#{limit}"})
    public List<Comment> selectByEntityAndOffset(@Param("entityId") int entityId, @Param("entityType") int entityType, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where 1=1 and status=0 and entity_id=#{entityId} and entity_type=#{entityType}"})
    public int selectCountByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);
}