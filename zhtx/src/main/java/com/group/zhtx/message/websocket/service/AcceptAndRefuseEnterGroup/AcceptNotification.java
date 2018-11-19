package com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup;

import com.group.zhtx.message.IMessage;


public class AcceptNotification implements IMessage {

    private int operateId;
    private String status;
    private String groupNumber;
    private String groupName;
    private String groupPortarit;

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

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPortarit() {
        return groupPortarit;
    }

    public void setGroupPortarit(String groupPortarit) {
        this.groupPortarit = groupPortarit;
    }
}
