/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TeachingMaterial
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:01
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
public class TeachingMaterial extends BaseEntity {
    private String name;
    private String author;
    private String moreAuthor;
    private Integer wordNumber;
    private String area;
    private String type;
    private String publishLevel;
    private String publishUnit;
    private String publishAddress;
    private String isbn;
    private String publishDate;
    private String resultScore;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public TeachingMaterial setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public TeachingMaterial setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getMoreAuthor() {
        return moreAuthor;
    }

    public TeachingMaterial setMoreAuthor(String moreAuthor) {
        this.moreAuthor = moreAuthor;
        return this;
    }

    public Integer getWordNumber() {
        return wordNumber;
    }

    public TeachingMaterial setWordNumber(Integer wordNumber) {
        this.wordNumber = wordNumber;
        return this;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPublishLevel() {
        return publishLevel;
    }

    public void setPublishLevel(String publishLevel) {
        this.publishLevel = publishLevel;
    }

    public String getType() {
        return type;
    }

    public TeachingMaterial setType(String type) {
        this.type = type;
        return this;
    }

    public String getPublishUnit() {
        return publishUnit;
    }

    public TeachingMaterial setPublishUnit(String publishUnit) {
        this.publishUnit = publishUnit;
        return this;
    }

    public String getPublishAddress() {
        return publishAddress;
    }

    public TeachingMaterial setPublishAddress(String publishAddress) {
        this.publishAddress = publishAddress;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public TeachingMaterial setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public TeachingMaterial setPublishDate(String publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public TeachingMaterial setResultScore(String resultScore) {
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

    public TeachingMaterial setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "TeachingMaterial{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", moreAuthor='" + moreAuthor + '\'' +
                ", wordNumber=" + wordNumber +
                ", type='" + type + '\'' +
                ", publishUnit='" + publishUnit + '\'' +
                ", publishAddress='" + publishAddress + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishDate=" + publishDate +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}