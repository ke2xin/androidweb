package com.group.zhtx.message.websocket.service.enterGroup;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class EnterGroupDataS implements IMessage {
    private int operateId;
    private String status;
    private List<EnterDataInfo> data = new ArrayList<>();

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

    public List<EnterDataInfo> getData() {
        return data;
    }

    public void setData(List<EnterDataInfo> data) {
        this.data = data;
    }
}
