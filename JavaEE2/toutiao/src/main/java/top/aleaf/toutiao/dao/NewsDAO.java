/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: NewsDAO
 * Author:   郭新晔
 * Date:     2018/11/22 0022 22:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.toutiao.model.News;
import top.aleaf.toutiao.model.User;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/11/22 0022
 */
@Repository
@Mapper
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title,link,image,like_count,comment_count,create_date,user_id ";
    String INSERT_VALUES = " #{title},#{link},#{image},#{likeCount},#{commentCount},#{createDate},#{userId} ";
    String SELECT_FIELDS = " id,title,link,image,like_count,comment_count,create_date,user_id ";

    /**
     * 添加咨询
     *
     * @param news 需要插入的咨询
     * @return 受影响的行数
     */
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    int addNews(News news);

    /**
     * 按照用户ID分页查询资讯文章列表
     *
     * @param userId 用户ID，userId为0时全查
     * @param offset 分页起始记录
     * @param limit  每一页的记录数
     * @return 返回资讯文章列表
     */
    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);

    /**
     * 按照咨询ID查询咨询
     *
     * @param newsId 咨询ID
     * @return 返回查询结果
     */
    News selectNewsById(@Param("newsId") int newsId);

    /**
     * 按照咨询ID查询资讯的发布者
     *
     * @param newsId 咨询ID
     * @return 返回查询结果
     */
    @Select({"select * from user where id=(select user_id from ",TABLE_NAME," where id=#{newsId})"})
    User selectUserById(@Param("newsId") int newsId);

    @Update({"update ", TABLE_NAME, " set comment_count=#{commentCount} where id=#{newsId}"})
    void updateNewsCommentCount(@Param("newsId") int newsId, @Param("commentCount") int commentCount);
    @Update({"update ", TABLE_NAME, " set like_count=#{likeCount} where id=#{newsId}"})
    void updateNewsLikeCount(@Param("newsId") int newsId, @Param("likeCount") int likeCount);
}