/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AcademicExchange
 * Author:   郭新晔
 * Date:     2019/2/11 0011 15:15
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
public class AcademicExchange extends BaseEntity {
    private String meetingName;
    private String meetingDate;
    private String meetingAddress;
    private String mainParticipant;
    private String college;
    private String resultScore;
    private String remark;

    private Integer createdId;
    private Date createdDate;

    private Integer status = 0;

    public String getMeetingName() {
        return meetingName;
    }

    public AcademicExchange setMeetingName(String meetingName) {
        this.meetingName = meetingName;
        return this;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public AcademicExchange setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
        return this;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public AcademicExchange setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
        return this;
    }

    public String getMainParticipant() {
        return mainParticipant;
    }

    public AcademicExchange setMainParticipant(String mainParticipant) {
        this.mainParticipant = mainParticipant;
        return this;
    }

    public String getCollege() {
        return college;
    }

    public AcademicExchange setCollege(String college) {
        this.college = college;
        return this;
    }

    public String getResultScore() {
        return resultScore;
    }

    public AcademicExchange setResultScore(String resultScore) {
        this.resultScore = resultScore;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AcademicExchange setRemark(String remark) {
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

    public AcademicExchange setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "AcademicExchange{" +
                "meetingName='" + meetingName + '\'' +
                ", meetingDate=" + meetingDate +
                ", meetingAddress='" + meetingAddress + '\'' +
                ", mainParticipant='" + mainParticipant + '\'' +
                ", college='" + college + '\'' +
                ", resultScore='" + resultScore + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}