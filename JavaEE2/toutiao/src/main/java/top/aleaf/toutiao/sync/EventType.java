package top.aleaf.toutiao.sync;

/**
 * Created by 郭新晔 on 2019/1/9 0009.
 */
public enum EventType {
    LIKE(1),
    COMMIT(2),
    LOGIN(3),
    MAIL(4),
    MESSAGE(5);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
