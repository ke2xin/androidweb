package com.group.zhtx.message.websocket.service.deleteGroupData;

import com.group.zhtx.message.IMessage;



public class DeleteDataS implements  IMessage {
    private int operateId;
    private String status;
    private String delUuid;
    private DeleteInfo data;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDelUuid() {
        return delUuid;
    }

    public void setDelUuid(String delUuid) {
        this.delUuid = delUuid;
    }
}
