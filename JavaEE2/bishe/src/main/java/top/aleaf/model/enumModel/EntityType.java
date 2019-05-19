package top.aleaf.model.enumModel;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/4/7 0007
 */
public enum EntityType {
    /**
     * 横向项目
     */
    COMPANY_PROJECT(1, "横向项目"),
    /**
     * 纵向项目
     */
    GOVERNMENT_PROJECT(2, "纵向项目"),
    /**
     * 获奖
     */
    PRIZE(3, "获奖"),
    /**
     * 教材
     */
    TEACHING_MATERIAL(4, "教材"),
    /**
     * 专利
     */
    PATENT(5, "专利"),
    /**
     * 软件著作
     */
    SOFTWARE_WORK(6, "软件著作"),
    /**
     * 学术讲座
     */
    LECTURE(7, "学术讲座"),
    /**
     * 学术交流
     */
    ACADEMIC_EXCHANGE(8, "学术交流"),
    /**
     * 论文
     */
    PAPER(9, "论文"),
    /**
     * 用户
     */
    USER(10, "用户");

    private int value;
    private String desc;
    public static final Map<Integer, EntityType> valueMap = Maps.newHashMap();

    static {
        for (EntityType item : EntityType.values()) {
            valueMap.put(item.getValue(), item);
        }
    }

    EntityType(int value, String desc) {
        this.value = value;
        this.desc = desc;
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