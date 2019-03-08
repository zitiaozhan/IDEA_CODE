/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Comment
 * Author:   郭新晔
 * Date:     2019/1/17 0017 21:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 〈〉
 *
 * @create 2019/1/17 0017
 */
@Component
public class Comment extends BaseEntity {
    private String content;
    private Integer userId;
    private Integer entityId;
    private String entityType;
    private Date createdDate;
    private Integer status = 0;

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Comment setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public Comment setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public Comment setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Comment setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Comment setStatus(Integer status) {
        this.status = status == null ? 0 : status;
        return this;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userId=" + userId +
                ", entityId=" + entityId +
                ", entityType='" + entityType + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }
}