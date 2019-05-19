/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Edu
 * Author:   郭新晔
 * Date:     2018/6/21 0021 21:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xsyu.po;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 郭新晔
 * @create 2018/6/21 0021
 * @since 1.0.0
 */
public class Edu {
    private Integer eduId;
    private String edu;

    public Edu(Integer eduId, String edu) {
        this.eduId = eduId;
        this.edu = edu;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }
}