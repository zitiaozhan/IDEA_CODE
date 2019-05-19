/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Prize
 * Author:   郭新晔
 * Date:     2019/2/11 0011 14:58
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
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Component
public class Prize extends BaseEntity {
    private String name;
    private String author;
    private String guidanceTeacher;
    private String certificateId;
    private String prizeType;
    private String prizeLevel;
    private String awardUnit;
    private String prizeDate;
    private String resultScore;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public Prize setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Prize setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getGuidanceTeacher() {
        return guidanceTeacher;
    }

    public Prize setGuidanceTeacher(String guidanceTeacher) {
        this.guidanceTeacher = guidanceTeacher;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public Prize setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getPrizeLevel() {
        return prizeLevel;
    }

    public Prize setPrizeLevel(String prizeLevel) {
        this.prizeLevel = prizeLevel;
        return this;
    }

    public String getAwardUnit() {
        return awardUnit;
    }

    public Prize setAwardUnit(String awardUnit) {
        this.awardUnit = awardUnit;
        return this;
    }

    public String getPrizeDate() {
        return prizeDate;
    }

    public Prize setPrizeDate(String prizeDate) {
        this.prizeDate = prizeDate;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public Prize setResultScore(String resultScore) {
        this.resultScore = resultScore;
        return this;
    }

    public Integer getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public Prize setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", guidanceTeacher='" + guidanceTeacher + '\'' +
                ", certificateId='" + certificateId + '\'' +
                ", prizeLevel='" + prizeLevel + '\'' +
                ", awardUnit='" + awardUnit + '\'' +
                ", prizeDate=" + prizeDate +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}