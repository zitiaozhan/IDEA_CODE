/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Strings
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:26
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
public class Strings extends BaseEntity {
    private Integer entityType;
    private String entityField;
    private String content;
    private String remark;
    private Integer status = 0;

    public Integer getEntityType() {
        return entityType;
    }

    public Strings setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getEntityField() {
        return entityField;
    }

    public Strings setEntityField(String entityField) {
        this.entityField = entityField;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Strings setContent(String content) {
        this.content = content;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public Strings setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Strings{" +
                "entityType=" + entityType +
                ", entityField='" + entityField + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
