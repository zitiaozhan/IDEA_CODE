package top.aleaf.wenda.model;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Transient;
import java.util.Date;

public class Feed extends BaseEntity {
    private Integer type;
    private Integer userId;
    private Integer status = 0;
    private Date createdDate;
    //存储的是对象的JSON字符串
    private String data;

    @Transient
    private JSONObject dataJSON = null;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }

    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }
}
