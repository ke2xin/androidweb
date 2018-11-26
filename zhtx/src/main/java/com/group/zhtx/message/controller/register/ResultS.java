package com.group.zhtx.message.controller.register;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultS implements Serializable, IMessage {
    private String operateId;
    private String status;
    private List<Object> data = new ArrayList<>();

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
