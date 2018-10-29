package com.group.zhtx.message.websocket.service.myData;

import com.group.zhtx.message.IMessage;

public class MyDataS implements IMessage {
    private int operateId;
    private String status;
    private MyDataInfo data;

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

    public MyDataInfo getData() {
        return data;
    }

    public void setData(MyDataInfo data) {
        this.data = data;
    }
}
