package top.aleaf.model.enumModel;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author 郭新晔
 */
public enum UserRoleEnum {
    /**
     * 教师
     */
    TEACHER("teacher", "教师", 3),
    /**
     * 管理员
     */
    MANAGER("manager", "管理员", 2),
    /**
     * 超级管理员
     */
    ADMIN("smanager", "超级管理员", 1);

    private String value;
    private String desc;
    private int id;
    public static final Map<Integer, UserRoleEnum> valueMap = Maps.newHashMap();

    private UserRoleEnum(String value, String desc, int id) {
        this.value = value;
        this.desc = desc;
        this.id = id;
    }

    static {
        for (UserRoleEnum item : UserRoleEnum.values()) {
            valueMap.put(item.getId(), item);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
