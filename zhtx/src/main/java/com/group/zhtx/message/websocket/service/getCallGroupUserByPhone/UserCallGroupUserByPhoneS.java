package com.group.zhtx.message.websocket.service.getCallGroupUserByPhone;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class UserCallGroupUserByPhoneS implements IMessage {

    private int operateId;
    private String status;
    private List<GroupUserByCallData> data = new ArrayList<>();


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

    public List<GroupUserByCallData> getData() {
        return data;
    }

    public void setData(List<GroupUserByCallData> data) {
        this.data = data;
    }
}
