package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserLocationInfoC implements Serializable, IMessage {
    private int operateId;
    private String groupId;

    public UserLocationInfoC() {
    }

    public UserLocationInfoC(int operateId, String groupId) {
        this.operateId = operateId;
        this.groupId = groupId;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
