package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserDeleteGroupNumberC implements Serializable, IMessage {
    private int operateId;
    private String groupId;
    private String uuid;
    private String delUuid;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDelUuid() {
        return delUuid;
    }

    public void setDelUuid(String delUuid) {
        this.delUuid = delUuid;
    }
}
