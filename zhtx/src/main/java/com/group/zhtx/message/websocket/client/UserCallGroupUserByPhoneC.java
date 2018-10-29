package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserCallGroupUserByPhoneC implements IMessage {

    private int operateId;
    private String groupId;

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
