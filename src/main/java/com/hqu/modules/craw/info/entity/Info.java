package com.hqu.modules.craw.info.entity;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.IdGen;
import net.oschina.j2cache.util.Serializer;

import java.io.Serializable;
import java.util.Date;

public class Info implements Serializable {
    private String id;
    private Date createDate;
    private JSONObject object;
    private String taskId;
    private String url;

    public Info() {
    }

    public Info(JSONObject object, String taskId, String url) {
        this.id = IdGen.uuid();
        this.createDate = new Date();
        this.object = object;
        this.taskId = taskId;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
