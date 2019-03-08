/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CompanyProject
 * Author:   郭新晔
 * Date:     2019/2/11 0011 14:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.model;

import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 〈〉
 *
 * @create 2019/2/11 0011
 */
@Component
public class CompanyProject extends BaseEntity {
    private String name;
    private String projectDate;
    private Integer chargePerson;
    private String chargeGroup;
    private String participant;
    private String projectSource;
    private String projectType;
    private Float contractMoney;
    private Float arrivalMoney;
    private String arrivalDate;
    private String completeDate;
    private String projectStatus;

    private Integer createdId;
    private Date createdDate;

    private String resultScore;

    @Transient
    private String helpContent;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public CompanyProject setName(String name) {
        this.name = name;
        return this;
    }

    public String getProjectDate() {
        return projectDate;
    }

    public CompanyProject setProjectDate(String projectDate) {
        this.projectDate = projectDate;
        return this;
    }

    public Integer getChargePerson() {
        return chargePerson;
    }

    public CompanyProject setChargePerson(Integer chargePerson) {
        this.chargePerson = chargePerson;
        return this;
    }

    public String getChargeGroup() {
        return chargeGroup;
    }

    public CompanyProject setChargeGroup(String chargeGroup) {
        this.chargeGroup = chargeGroup;
        return this;
    }

    public String getParticipant() {
        return participant;
    }

    public CompanyProject setParticipant(String participant) {
        this.participant = participant;
        return this;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public CompanyProject setProjectSource(String projectSource) {
        this.projectSource = projectSource;
        return this;
    }

    public String getProjectType() {
        return projectType;
    }

    public CompanyProject setProjectType(String projectType) {
        this.projectType = projectType;
        return this;
    }

    public Float getContractMoney() {
        return contractMoney;
    }

    public CompanyProject setContractMoney(Float contractMoney) {
        this.contractMoney = contractMoney;
        return this;
    }

    public Float getArrivalMoney() {
        return arrivalMoney;
    }

    public CompanyProject setArrivalMoney(Float arrivalMoney) {
        this.arrivalMoney = arrivalMoney;
        return this;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public CompanyProject setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public CompanyProject setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public CompanyProject setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public CompanyProject setResultScore(String resultScore) {
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

    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
    }

    public CompanyProject setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyProject{" +
                "name='" + name + '\'' +
                ", projectDate='" + projectDate + '\'' +
                ", chargePerson=" + chargePerson +
                ", chargeGroup='" + chargeGroup + '\'' +
                ", participant='" + participant + '\'' +
                ", projectSource='" + projectSource + '\'' +
                ", projectType='" + projectType + '\'' +
                ", contractMoney=" + contractMoney +
                ", arrivalMoney=" + arrivalMoney +
                ", arrivalDate=" + arrivalDate +
                ", completeDate=" + completeDate +
                ", projectStatus='" + projectStatus + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}