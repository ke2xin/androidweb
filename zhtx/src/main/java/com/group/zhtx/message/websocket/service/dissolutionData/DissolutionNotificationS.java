package com.group.zhtx.message.websocket.service.dissolutionData;

import com.group.zhtx.message.IMessage;



public class DissolutionNotificationS implements IMessage {
    private int role;//0表示群主，1表示群成员
    private int operateId;
    private String status;
    private DissolutionNotificationInfo data;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

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

    public DissolutionNotificationInfo getData() {
        return data;
    }

    public void setData(DissolutionNotificationInfo data) {
        this.data = data;
    }
}
