/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SoftwareWork
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:08
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
public class SoftwareWork extends BaseEntity {
    private String name;
    private String copyrightOwner;
    private String completeDate;
    private String firstPublishDate;
    private String ownWay;
    private String rightRange;
    private String publishNumber;
    private String certificateId;
    private String awardDate;
    private String author;
    private String resultScore;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public SoftwareWork setName(String name) {
        this.name = name;
        return this;
    }

    public String getCopyrightOwner() {
        return copyrightOwner;
    }

    public SoftwareWork setCopyrightOwner(String copyrightOwner) {
        this.copyrightOwner = copyrightOwner;
        return this;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public SoftwareWork setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public String getFirstPublishDate() {
        return firstPublishDate;
    }

    public SoftwareWork setFirstPublishDate(String firstPublishDate) {
        this.firstPublishDate = firstPublishDate;
        return this;
    }

    public String getOwnWay() {
        return ownWay;
    }

    public SoftwareWork setOwnWay(String ownWay) {
        this.ownWay = ownWay;
        return this;
    }

    public String getRightRange() {
        return rightRange;
    }

    public SoftwareWork setRightRange(String rightRange) {
        this.rightRange = rightRange;
        return this;
    }

    public String getPublishNumber() {
        return publishNumber;
    }

    public SoftwareWork setPublishNumber(String publishNumber) {
        this.publishNumber = publishNumber;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public SoftwareWork setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getAwardDate() {
        return awardDate;
    }

    public SoftwareWork setAwardDate(String awardDate) {
        this.awardDate = awardDate;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public SoftwareWork setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public SoftwareWork setResultScore(String resultScore) {
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

    public SoftwareWork setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "SoftwareWork{" +
                "name='" + name + '\'' +
                ", copyrightOwner='" + copyrightOwner + '\'' +
                ", completeDate=" + completeDate +
                ", firstPublishDate=" + firstPublishDate +
                ", ownWay='" + ownWay + '\'' +
                ", rightRange='" + rightRange + '\'' +
                ", publishNumber='" + publishNumber + '\'' +
                ", certificateId='" + certificateId + '\'' +
                ", awardDate=" + awardDate +
                ", author='" + author + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}