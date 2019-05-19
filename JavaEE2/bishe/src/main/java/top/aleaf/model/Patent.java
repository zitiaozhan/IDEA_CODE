/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Patent
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:05
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
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Component
public class Patent extends BaseEntity{
    private String name;
    private String certificateId;
    private String author;
    private String moreAuthor;
    private String patentNumber;
    private String type;
    private String applyDate;
    private String patentedPerson;
    private String awardDate;
    private String resultScore;

    private Integer createdId;
    private Date createdDate;

    private Integer status=0;

    public String getName() {
        return name;
    }

    public Patent setName(String name) {
        this.name = name;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public Patent setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Patent setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getMoreAuthor() {
        return moreAuthor;
    }

    public Patent setMoreAuthor(String moreAuthor) {
        this.moreAuthor = moreAuthor;
        return this;
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    public Patent setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public Patent setApplyDate(String applyDate) {
        this.applyDate = applyDate;
        return this;
    }

    public String getPatentedPerson() {
        return patentedPerson;
    }

    public Patent setPatentedPerson(String patentedPerson) {
        this.patentedPerson = patentedPerson;
        return this;
    }

    public String getAwardDate() {
        return awardDate;
    }

    public Patent setAwardDate(String awardDate) {
        this.awardDate = awardDate;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public Patent setResultScore(String resultScore) {
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

    public Patent setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Patent{" +
                "name='" + name + '\'' +
                ", certificateId='" + certificateId + '\'' +
                ", author=" + author +
                ", moreAuthor='" + moreAuthor + '\'' +
                ", patentNumber='" + patentNumber + '\'' +
                ", applyDate=" + applyDate +
                ", patentedPerson='" + patentedPerson + '\'' +
                ", awardDate=" + awardDate +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}