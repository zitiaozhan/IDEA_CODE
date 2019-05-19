/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProjectScoreParseVO
 * Author:   郭新晔
 * Date:     2019/3/10 0010 17:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈每个项目的科技分解析实体类(非表)〉
 *
 * @author 郭新晔
 * @create 2019/3/10 0010
 */
@Data
public class ProjectScoreParseVO {
    //实体类型
    private Integer entityType;
    //实体ID
    private Integer entityId;
    //项目名称
    private String name;
    //作者列表
    private List<String> authorList;
    //项目总分
    private Double projectScore;
    //作者分数对应
    private Map<String, Double> authorScoreMap;
    //解析时间
    private Date parseDate;
    //是否跳过第一人
    private Boolean isSkipFirst = false;
}