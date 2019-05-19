package top.aleaf.controller.vo;

import lombok.Data;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/5/5 0005
 * Author:   郭新晔
 */
@Data
public class EntityProjectVO {
    private String entityName;
    private String entityType;
    //项目创建日期/审批日期
    private String date;
    //项目总分
    private Double projectScore;
    //我的科技分 或 管理的审核数量
    private Double myScore;
    //分数占比
    private Double scale;
    //审批结果
    private String approvalResult;
}