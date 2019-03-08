/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Role
 * Author:   郭新晔
 * Date:     2019/2/11 0011 14:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Component
public class Role extends BaseEntity {
    private String name;
    private String detail;
    private Integer status = 0;

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Role setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Role setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                '}';
    }
}