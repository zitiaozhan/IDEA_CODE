/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: User
 * Author:   郭新晔
 * Date:     2019/1/17 0017 21:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @create 2019/1/17 0017
 */
@Component
public class User extends BaseEntity {
    private String name;
    private String password;
    private String salt;
    private String headUrl;
    private Integer status = 0;
    private String mail;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public User setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public User setStatus(Integer status) {
        this.status = status == null ? 0 : status;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", status=" + status +
                ", mail='" + mail + '\'' +
                '}';
    }
}