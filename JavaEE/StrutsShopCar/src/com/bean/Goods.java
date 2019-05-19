/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Goods
 * Author:   郭新晔
 * Date:     2018/5/8 0008 20:05
 * Description: 货物类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bean;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈货物类〉
 *
 * @author 郭新晔
 * @create 2018/5/8 0008
 * @since 1.0.0
 */
public class Goods implements Serializable{
    private Integer id;
    private String goodName;
    private Float goodPrice;
    private Integer goodCount;
    private Float goodSum;

    public Goods(){}

    public Goods(Integer id, String goodName, Float goodPrice, Integer goodCount, Float goodSum) {
        this.id = id;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
        this.goodCount = goodCount;
        this.goodSum = goodSum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Float getGoodSum() {
        return this.goodPrice * this.goodCount;
    }

    public void setGoodSum(Float goodSum) {
        this.goodSum = goodSum;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", goodPrice=" + goodPrice +
                ", goodCount=" + goodCount +
                ", goodSum=" + goodSum +
                '}';
    }
}