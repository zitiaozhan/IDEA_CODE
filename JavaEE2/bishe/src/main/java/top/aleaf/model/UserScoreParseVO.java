/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserScoreParseVO
 * Author:   郭新晔
 * Date:     2019/4/6 0006 14:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 〈每个用户的科技分解析类，每个用户只有一个〉
 * @author 郭新晔
 * @create 2019/4/6 0006
 */
@Data
public class UserScoreParseVO {
    //项目分数映射
    private Map<ProjectScoreParseVO,Double> projectScoreMap;
    //更新时间
    private Date modify;
}