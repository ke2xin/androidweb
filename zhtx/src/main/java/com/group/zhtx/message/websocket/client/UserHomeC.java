package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserHomeC implements IMessage {
    private int operateId;
    private String uuid;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
