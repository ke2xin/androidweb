package com.group.zhtx.message.websocket.service.sendUserLocation;


import com.group.zhtx.message.IMessage;


public class SendUserLocationS implements IMessage {
    private int operateId;
    private String status;
    private SendUserLocationInfo data;
    private String information;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public SendUserLocationInfo getData() {
        return data;
    }

    public void setData(SendUserLocationInfo data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
