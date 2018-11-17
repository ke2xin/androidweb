package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserRelativNumberC implements Serializable, IMessage {
    private int operateId;
    private String groupId;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroup_id() {
        return groupId;
    }

    public void setGroup_id(String group_id) {
        this.groupId = group_id;
    }
}
