/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProjectType
 * Author:   郭新晔
 * Date:     2019/2/13 0013 16:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

/**
 * 〈〉
 * @create 2019/2/13 0013
 */
@Component
public class InfoType extends BaseEntity{
    private String entityType;
    private String entityName;
    private String remark;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}