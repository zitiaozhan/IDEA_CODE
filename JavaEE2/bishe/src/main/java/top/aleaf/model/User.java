/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: User
 * Author:   郭新晔
 * Date:     2019/2/11 0011 14:14
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
public class User extends BaseEntity {
    private String number;
    private String name;
    private String password;
    private String salt;
    private Integer roleId;
    private String profession;
    private String phone;
    private String mail;
    private Date regDate;
    private Integer status = 0;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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

    public Integer getRoleId() {
        return roleId;
    }

    public User setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getProfession() {
        return profession;
    }

    public User setProfession(String profession) {
        this.profession = profession;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getStatus() {
        return status;
    }

    public User setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", profession='" + profession + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", status=" + status +
                '}';
    }
}