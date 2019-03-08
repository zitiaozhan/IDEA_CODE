package top.aleaf.wenda.model;

/**
 * Created by 郭新晔 on 2019/1/20 0020.
 */
public enum EntityType {
    ENTITY_QUESTION("1"),
    ENTITY_COMMENT("2"),
    ENTITY_ADMIN_MAIL("3"),
    ENTITY_USER("4");

    private String value;

    EntityType(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
