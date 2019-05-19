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
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.aleaf.model.Approval;

import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Repository
public interface ApprovalMapper extends Mapper<Approval> {

    @Select("SELECT user_id,SUM(1) AS result FROM approval GROUP BY user_id ORDER BY result DESC,user_id ASC LIMIT #{limit};")
    List<Approval> selectUserByApprovalNum(@Param("limit") int limit);
}