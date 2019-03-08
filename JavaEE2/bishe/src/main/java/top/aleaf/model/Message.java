/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Message
 * Author:   郭新晔
 * Date:     2019/1/17 0017 21:33
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
 */
@Component
public class Message extends BaseEntity {
    private Integer fromId;
    private Integer toId;
    private String conversationId;
    private Integer hasRead;
    private String content;
    private Date createdDate;
    private Integer status = 0;

    public Integer getFromId() {
        return fromId;
    }

    public Message setFromId(Integer fromId) {
        this.fromId = fromId;
        return this;
    }

    public Integer getToId() {
        return toId;
    }

    public Message setToId(Integer toId) {
        this.toId = toId;
        return this;
    }

    public String getConversationId() {
        return conversationId;
    }

    public Message setConversationId(String conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public Message setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Message setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Message setStatus(Integer status) {
        this.status = status == null ? 0 : status;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }
}