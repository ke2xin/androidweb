package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserLocationInfoC implements Serializable, IMessage {
    private int operateId;
    private String group_id;

    public UserLocationInfoC() {
    }

    public UserLocationInfoC(int operateId, String group_id) {
        this.operateId = operateId;
        this.group_id = group_id;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
