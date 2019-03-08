/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventType
 * Author:   郭新晔
 * Date:     2019/1/22 0022 21:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.sync;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
public enum EventType {
    APPROVE(1);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}