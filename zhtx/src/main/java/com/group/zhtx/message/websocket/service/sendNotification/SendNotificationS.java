package com.group.zhtx.message.websocket.service.sendNotification;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class SendNotificationS implements IMessage {

    private int operateId;
    private String status;
    private List<SendNotification> data = new ArrayList<>();

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

    public List<SendNotification> getData() {
        return data;
    }

    public void addNotification(SendNotification sendNotification) {
        data.add(sendNotification);
    }

    public void setData(List<SendNotification> data) {
        this.data = data;
    }
}
