package com.group.zhtx.message.websocket.service.dissolutionData;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class DissolutionDataS implements IMessage {
    private int operateId;
    private String status;
    private String information;
    private List<DissolutionInfo>groups=new ArrayList<>();
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void addGroup(DissolutionInfo group){
        groups.add(group);
    }
}
