/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Detail
 * Author:   郭新晔
 * Date:     2018/12/18 0018 10:05
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
 * @create 2018/12/18 0018
 */
@Component
public class Detail {
    private int id;
    private String detailImg;
    private Date buyDate;
    private String networkType;
    private String osType;
    private String powerType;
    private String coreNum;
    private float land;
    private String ratio;
    private float screenSize;
    private String bcType;
    private String fcType;
    private String otherFunction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getCoreNum() {
        return coreNum;
    }

    public void setCoreNum(String coreNum) {
        this.coreNum = coreNum;
    }

    public float getLand() {
        return land;
    }

    public void setLand(float land) {
        this.land = land;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getBcType() {
        return bcType;
    }

    public void setBcType(String bcType) {
        this.bcType = bcType;
    }

    public String getFcType() {
        return fcType;
    }

    public void setFcType(String fcType) {
        this.fcType = fcType;
    }

    public String getOtherFunction() {
        return otherFunction;
    }

    public void setOtherFunction(String otherFunction) {
        this.otherFunction = otherFunction;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", detailImg='" + detailImg + '\'' +
                ", buyDate=" + buyDate +
                ", networkType='" + networkType + '\'' +
                ", osType='" + osType + '\'' +
                ", powerType='" + powerType + '\'' +
                ", coreNum='" + coreNum + '\'' +
                ", land=" + land +
                ", ratio='" + ratio + '\'' +
                ", screenSize=" + screenSize +
                ", bcType='" + bcType + '\'' +
                ", fcType='" + fcType + '\'' +
                ", otherFunction='" + otherFunction + '\'' +
                '}';
    }
}