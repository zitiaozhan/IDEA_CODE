/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Approval
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Component
public class Approval extends BaseEntity {
    private Integer entityId;
    private Integer entityType;
    private Integer userId;
    private Integer result;
    private Date date;
    private String remark;
    private Integer status = 0;

    public Integer getEntityId() {
        return entityId;
    }

    public Approval setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public Approval setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Approval setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public Approval setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Approval setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "entityId=" + entityId +
                ", entityType=" + entityType +
                ", userId=" + userId +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}