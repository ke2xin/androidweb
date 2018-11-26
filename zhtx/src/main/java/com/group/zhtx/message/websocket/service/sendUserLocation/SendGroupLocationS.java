package com.group.zhtx.message.websocket.service.sendUserLocation;

import com.group.zhtx.message.IMessage;
import com.group.zhtx.model.UserGps;

import java.util.ArrayList;
import java.util.List;

public class SendGroupLocationS implements IMessage {
    private int operateId;
    private String status;
    private List<SendGroupLocationData> data = new ArrayList<>();
    private String des;

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


    public List<SendGroupLocationData> getData() {
        return data;
    }

    public void setData(List<SendGroupLocationData> data) {
        this.data = data;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void addSendData(SendGroupLocationData sendGroupLocationData) {
        data.add(sendGroupLocationData);
    }
}
