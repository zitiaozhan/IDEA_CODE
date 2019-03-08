/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SummaryDAO
 * Author:   郭新晔
 * Date:     2018/12/18 0018 10:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.model.Summary;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/18 0018
 */
@Mapper
@Repository
public interface SummaryDAO {
    String TABLE_NAME = "summary";
    String INSERT_FIELDS = " name,brand,price,cpu_type,memory,head_img,detail_id,status,score ";
    String INSERT_VALUES = " #{name},#{brand},#{price},#{cpuType},#{memory},#{headImg},#{detailId},#{status},#{score} ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    public int addPhone(Summary summary);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and id=#{id} and status!=1"})
    public Summary selectPhoneById(@Param("id") int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and status!=1 order by id desc limit #{offset},#{limit}"})
    public List<Summary> selectPhoneByOffsetAndLimit(@Param("offset") int offset, @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    public void updateStatus(@Param("status") int status, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set detail_id=#{detailId} where id=#{id}"})
    public void updateDetailId(@Param("detailId") int detailId, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set name=#{name},brand=#{brand},price=#{price},cpu_type=#{cpuType},memory=#{memory},head_img=#{headImg},detail_id=#{detailId},score=#{score} where 1=1 and id=#{id}"})
    public void updatePhone(Summary summary);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1 and status!=1 and (name like '%${keyword}%' or brand like '%${keyword}%') order by id desc limit #{offset},#{limit}"})
    public List<Summary> selectByKeyword(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(*) from ", TABLE_NAME, " where 1=1 and status!=1 and (name like '%${keyword}%' or brand like '%${keyword}%') "})
    public int selectByKeywordCount(@Param("keyword") String keyword);

    @Select({"select count(*) from ", TABLE_NAME, " where 1=1 and status!=1"})
    public int selectPhoneCount();

}