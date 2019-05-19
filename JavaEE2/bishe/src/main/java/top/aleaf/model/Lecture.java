/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Lecture
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:12
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
public class Lecture extends BaseEntity {
    private String name;
    private String rapporteur;
    private String rapporteurUnit;
    private String date;
    private String address;
    private String holdUnit;
    private String resultScore;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public Lecture setName(String name) {
        this.name = name;
        return this;
    }

    public String getRapporteur() {
        return rapporteur;
    }

    public Lecture setRapporteur(String rapporteur) {
        this.rapporteur = rapporteur;
        return this;
    }

    public String getRapporteurUnit() {
        return rapporteurUnit;
    }

    public Lecture setRapporteurUnit(String rapporteurUnit) {
        this.rapporteurUnit = rapporteurUnit;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Lecture setDate(String date) {
        this.date = date;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Lecture setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getHoldUnit() {
        return holdUnit;
    }

    public Lecture setHoldUnit(String holdUnit) {
        this.holdUnit = holdUnit;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public Lecture setResultScore(String resultScore) {
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

    public Lecture setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                ", rapporteur=" + rapporteur +
                ", rapporteurUnit='" + rapporteurUnit + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", holdUnit='" + holdUnit + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}