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
package top.aleaf.toutiao.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 〈〉
 * @create 2018/11/28 0028
 */
@Component
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private String ticket;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}