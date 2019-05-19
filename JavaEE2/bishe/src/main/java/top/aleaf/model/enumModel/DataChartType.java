package top.aleaf.model.enumModel;/**
 * Created by 郭新晔 on 2019/5/7 0007.
 */

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 〈数据图表类型〉
 *
 * @author 郭新晔
 * @create 2019/5/7 0007
 * Author:   郭新晔
 */
public enum DataChartType {
    BAR(1,"柱状图"),
    PIE(2,"饼状图");

    private int value;
    private String desc;
    public static final Map<Integer, DataChartType> valueMap = Maps.newHashMap();

    DataChartType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    static {
        for (DataChartType item : DataChartType.values()) {
            valueMap.put(item.getValue(), item);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
