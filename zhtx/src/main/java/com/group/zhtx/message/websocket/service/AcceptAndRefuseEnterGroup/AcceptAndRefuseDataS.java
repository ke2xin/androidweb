package com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup;

import com.group.zhtx.message.IMessage;

public class AcceptAndRefuseDataS implements IMessage {
    private int operateId;
    private AcceptAndRefuseInfo data;
    private String information;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public AcceptAndRefuseInfo getData() {
        return data;
    }

    public void setData(AcceptAndRefuseInfo data) {
        this.data = data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
