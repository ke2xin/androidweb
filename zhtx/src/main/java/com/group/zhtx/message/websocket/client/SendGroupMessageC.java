package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class SendGroupMessageC implements IMessage {

    private int operateId;
    private String uuid;
    private String groupId;
    private String groupMessage;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return groupMessage;
    }

    public void setContent(String content) {
        this.groupMessage = content;
    }
}
