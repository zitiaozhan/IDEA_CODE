/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Question
 * Author:   郭新晔
 * Date:     2019/1/17 0017 21:31
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
public class Question extends BaseEntity {
    private String title;
    private String content;
    private Integer userId;
    private Date createdDate;
    private Integer commentCount;
    private Integer status = 0;

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Question setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Question setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Question setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Question setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Question setStatus(Integer status) {
        this.status = status == null ? 0 : status;
        return this;
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", commentCount=" + commentCount +
                ", status=" + status +
                '}';
    }
}