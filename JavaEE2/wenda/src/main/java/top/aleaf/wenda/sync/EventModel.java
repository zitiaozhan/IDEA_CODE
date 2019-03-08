/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EventModel
 * Author:   郭新晔
 * Date:     2019/1/22 0022 21:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.wenda.sync;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
public class EventModel {
    private int actorId;
    private EventType eventType;
    private int entityId;
    private String entityType;
    private int eventOwnerId;
    private Map<String, String> exts = new HashMap();

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventModel() {
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEventOwnerId() {
        return eventOwnerId;
    }

    public EventModel setEventOwnerId(int eventOwnerId) {
        this.eventOwnerId = eventOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    public String getValue(String key) {
        return exts.get(key);
    }

    public EventModel addExt(String key, String value) {
        this.exts.put(key, value);
        return this;
    }
}