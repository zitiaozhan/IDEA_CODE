/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: User
 * Author:   郭新晔
 * Date:     2018/6/17 0017 13:39
 * Description: User---用户实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.po;

/**
 * 〈一句话功能简述〉<br> 
 * 〈User---用户实体类〉
 *
 * @author 郭新晔
 * @create 2018/6/17 0017
 * @since 1.0.0
 */
public class User {
    private Integer uid;
    private String uname;
    private String pwd;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}