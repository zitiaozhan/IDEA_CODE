/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LoginTicketDAO
 * Author:   郭新晔
 * Date:     2018/11/28 0028 19:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.Date;

/**
 * 〈〉
 *
 */
@Component
@Table(name = "login_ticket")
public class LoginTicket extends BaseEntity {
    private Integer userId;
    private Date expired;
    private String ticket;
    private Integer status = 0;

    public Integer getUserId() {
        return userId;
    }

    public LoginTicket setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Date getExpired() {
        return expired;
    }

    public LoginTicket setExpired(Date expired) {
        this.expired = expired;
        return this;
    }

    public String getTicket() {
        return ticket;
    }

    public LoginTicket setTicket(String ticket) {
        this.ticket = ticket;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public LoginTicket setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "userId=" + userId +
                ", expired=" + expired +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                '}';
    }
}