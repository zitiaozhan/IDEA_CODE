/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventModel
 * Author:   郭新晔
 * Date:     2019/1/9 0009 15:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.sync;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/9 0009
 */
public class EventModel {
    //事件类型
    private EventType eventType;
    //事件的触发者
    private int actor;
    //触发实体
    private int entityType;
    private int entityId;
    //实体的拥有者
    private int entityOwnerId;
    //事件的扩展信息
    private Map<String, String> exts = new HashMap();

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventModel() {
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActor() {
        return actor;
    }

    public EventModel setActor(int actor) {
        this.actor = actor;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public String getExt(String key) {
        return this.exts.get(key);
    }

    public EventModel setExt(String key, String value) {
        exts=exts==null?new HashMap<>():exts;
        this.exts.put(key, value);
        return this;
    }
}