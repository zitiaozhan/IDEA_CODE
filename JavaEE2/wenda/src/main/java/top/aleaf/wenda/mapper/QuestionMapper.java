/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserMapper
 * Author:   郭新晔
 * Date:     2019/1/17 0017 21:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.aleaf.wenda.model.Question;

/**
 * 〈〉
 *
 * @create 2019/1/17 0017
 */
@Repository
public interface QuestionMapper extends Mapper<Question> {
    public static final String TABLE_NAME="question";

    @Update({"update ",TABLE_NAME," set comment_count=#{commentCount} where id=#{id}"})
    public void updateCommentCount(@Param("id")int id,@Param("commentCount")int commentCount);
}