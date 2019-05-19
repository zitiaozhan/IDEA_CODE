package top.aleaf.model.enumModel;/**
 * Created by 郭新晔 on 2019/5/7 0007.
 */

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 〈图标数据类型〉
 *
 * @create 2019/5/7 0007
 * Author:   郭新晔
 */
public enum ChartDataType {
    NUMBER_ENTITY_TYPE(1, "数量-类型", "项目来源信息", "项目数量（个）"),
    SCORE_ENTITY_TYPE(2, "科技分-类型", "科技分来源信息", "科技分（分）");

    private int value;
    private String desc;
    private String ext1;
    private String ext2;
    public static final Map<Integer, ChartDataType> valueMap = Maps.newHashMap();

    ChartDataType(int value, String desc, String ext1, String ext2) {
        this.value = value;
        this.desc = desc;
        this.ext1 = ext1;
        this.ext2 = ext2;
    }

    static {
        for (ChartDataType item : ChartDataType.values()) {
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

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}
