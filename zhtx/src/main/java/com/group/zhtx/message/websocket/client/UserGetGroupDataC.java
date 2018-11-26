package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserGetGroupDataC implements IMessage {

    private String operateId;
    private String groupId;

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
