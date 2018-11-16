package com.group.zhtx.message.websocket.service.Anoun;

import com.group.zhtx.message.IMessage;

public class AnouS implements IMessage {
    private int operateId;
    private String status;
    private String groupId;
    private String information;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
