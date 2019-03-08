/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Paper
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:17
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
public class Paper extends BaseEntity {
    private String name;
    private Integer firstAuthor;
    private String moreAuthor;
    private String firstSignatureUnit;
    private String periodicalName;
    private String publishDate;
    private String issueNumber;
    private String level;
    private String publishRange;
    private String resultScore;
    private String remark;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public Paper setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getFirstAuthor() {
        return firstAuthor;
    }

    public Paper setFirstAuthor(Integer firstAuthor) {
        this.firstAuthor = firstAuthor;
        return this;
    }

    public String getMoreAuthor() {
        return moreAuthor;
    }

    public Paper setMoreAuthor(String moreAuthor) {
        this.moreAuthor = moreAuthor;
        return this;
    }

    public String getFirstSignatureUnit() {
        return firstSignatureUnit;
    }

    public Paper setFirstSignatureUnit(String firstSignatureUnit) {
        this.firstSignatureUnit = firstSignatureUnit;
        return this;
    }

    public String getPeriodicalName() {
        return periodicalName;
    }

    public Paper setPeriodicalName(String periodicalName) {
        this.periodicalName = periodicalName;
        return this;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public Paper setPublishDate(String publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public Paper setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public Paper setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getPublishRange() {
        return publishRange;
    }

    public Paper setPublishRange(String publishRange) {
        this.publishRange = publishRange;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public Paper setResultScore(String resultScore) {
        this.resultScore = resultScore;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Paper setRemark(String remark) {
        this.remark = remark;
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

    public Paper setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "name='" + name + '\'' +
                ", firstAuthor=" + firstAuthor +
                ", moreAuthor='" + moreAuthor + '\'' +
                ", firstSignatureUnit='" + firstSignatureUnit + '\'' +
                ", periodicalName='" + periodicalName + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", issueNumber='" + issueNumber + '\'' +
                ", level='" + level + '\'' +
                ", publishRange='" + publishRange + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}