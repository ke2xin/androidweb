package com.group.zhtx.message.websocket.service.dissolutionData;

import com.group.zhtx.message.IMessage;

public class DissolutionDataS implements IMessage {
    private int operateId;
    private String status;
    private DissolutionInfo data;
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

    public DissolutionInfo getData() {
        return data;
    }

    public void setData(DissolutionInfo data) {
        this.data = data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
