package com.group.zhtx.message.websocket.service.groupNumberInfo;

import com.group.zhtx.message.IMessage;

public class GroupNumberDataS implements IMessage {
    private int operateId;
    private String status;
    private GroupNumberInfo data;

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

    public GroupNumberInfo getData() {
        return data;
    }

    public void setData(GroupNumberInfo data) {
        this.data = data;
    }
}
