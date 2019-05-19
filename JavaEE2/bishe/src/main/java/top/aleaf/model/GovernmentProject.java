/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GovernmentProject
 * Author:   郭新晔
 * Date:     2019/2/11 0011 14:53
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
 * @author 郭新晔
 * @create 2019/2/11 0011
 */
@Component
public class GovernmentProject extends BaseEntity {
    private String name;
    private String projectId;
    private String techCode;
    private String finaCode;
    private String chargePerson;
    private String chargeGroup;
    private String participant;
    private String projectDate;
    private String projectSource;
    private String projectType;
    private String arrivalDate;
    private Integer contractMoney;
    private Integer arrivalMoney;
    private String moneySource;
    private String projectStatus;
    private String completeDate;
    private String remark;
    private String resultScore;
    @Transient
    private String helpContent;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getName() {
        return name;
    }

    public GovernmentProject setName(String name) {
        this.name = name;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public GovernmentProject setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getTechCode() {
        return techCode;
    }

    public GovernmentProject setTechCode(String techCode) {
        this.techCode = techCode;
        return this;
    }

    public String getFinaCode() {
        return finaCode;
    }

    public GovernmentProject setFinaCode(String finaCode) {
        this.finaCode = finaCode;
        return this;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public GovernmentProject setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
        return this;
    }

    public String getChargeGroup() {
        return chargeGroup;
    }

    public GovernmentProject setChargeGroup(String chargeGroup) {
        this.chargeGroup = chargeGroup;
        return this;
    }

    public String getParticipant() {
        return participant;
    }

    public GovernmentProject setParticipant(String participant) {
        this.participant = participant;
        return this;
    }

    public String getProjectDate() {
        return projectDate;
    }

    public GovernmentProject setProjectDate(String projectDate) {
        this.projectDate = projectDate;
        return this;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public GovernmentProject setProjectSource(String projectSource) {
        this.projectSource = projectSource;
        return this;
    }

    public String getProjectType() {
        return projectType;
    }

    public GovernmentProject setProjectType(String projectType) {
        this.projectType = projectType;
        return this;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public GovernmentProject setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public Integer getContractMoney() {
        return contractMoney;
    }

    public GovernmentProject setContractMoney(Integer contractMoney) {
        this.contractMoney = contractMoney;
        return this;
    }

    public Integer getArrivalMoney() {
        return arrivalMoney;
    }

    public GovernmentProject setArrivalMoney(Integer arrivalMoney) {
        this.arrivalMoney = arrivalMoney;
        return this;
    }

    public String getMoneySource() {
        return moneySource;
    }

    public GovernmentProject setMoneySource(String moneySource) {
        this.moneySource = moneySource;
        return this;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public GovernmentProject setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
        return this;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public GovernmentProject setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public GovernmentProject setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public GovernmentProject setResultScore(String resultScore) {
        this.resultScore = resultScore;
        return this;
    }

    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
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

    public GovernmentProject setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "GovernmentProject{" +
                "name='" + name + '\'' +
                ", projectId='" + projectId + '\'' +
                ", techCode='" + techCode + '\'' +
                ", finaCode='" + finaCode + '\'' +
                ", chargePerson=" + chargePerson +
                ", chargeGroup='" + chargeGroup + '\'' +
                ", participant='" + participant + '\'' +
                ", projectDate='" + projectDate + '\'' +
                ", projectSource='" + projectSource + '\'' +
                ", projectType='" + projectType + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", contractMoney=" + contractMoney +
                ", arrivalMoney=" + arrivalMoney +
                ", moneySource='" + moneySource + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", completeDate=" + completeDate +
                ", remark='" + remark + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", status=" + status +
                '}';
    }
}