package com.group.zhtx.message.websocket.service.deleteGroupData;

import com.group.zhtx.message.IMessage;



public class DeleteDataS implements  IMessage {
    private int operateId;
    private String status;
    private DeleteInfo data;
    private String uuid;
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

    public DeleteInfo getData() {
        return data;
    }

    public void setData(DeleteInfo data) {
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
