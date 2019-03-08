/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DetailDAO
 * Author:   郭新晔
 * Date:     2018/12/18 0018 14:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.aleaf.model.Detail;

/**
 * 〈〉
 *
 * @create 2018/12/18 0018
 */
@Mapper
@Repository
public interface DetailDAO {
    String TABLE_NAME = "detail";
    String INSERT_FIELDS = " detail_img,buy_date,network_type,os_type,power_type,core_num,land,ratio,screen_size,bc_type,fc_type,other_function ";
    String INSERT_VALUES = " #{detailImg},#{buyDate},#{networkType},#{osType},#{powerType},#{coreNum},#{land},#{ratio},#{screenSize},#{bcType},#{fcType},#{otherFunction} ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values(", INSERT_VALUES, ")"})
    public int addPhoneDetail(Detail detail);

    @Update({"update ", TABLE_NAME, " set detail_img=#{detailImg},buy_date=#{buyDate},network_type=#{networkType},os_type=#{osType},power_type=#{powerType},core_num=#{coreNum},land=#{land},ratio=#{ratio},screen_size=#{screenSize},bc_type=#{bcType},fc_type=#{fcType},other_function=#{otherFunction} where 1=1 and id=#{id}"})
    public void updateDetail(Detail detail);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    public Detail selectDetailById(@Param("id") int id);
}