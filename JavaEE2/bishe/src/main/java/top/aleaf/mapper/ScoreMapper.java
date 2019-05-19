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
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.aleaf.model.Score;

import java.util.List;

/**
 * 〈〉
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Repository
public interface ScoreMapper extends Mapper<Score>{
    /**
     * 按用户编号查询
     * @param numberList  用户编号列表
     */
    List<Score> selectByNumberList(@Param("numberList") List<String> numberList);
}