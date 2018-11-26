package com.group.zhtx.message.websocket.service.loginMessage;

import com.group.zhtx.message.IMessage;

public class UserLoginS implements IMessage {

    private int operateId;

    private String status;

    private UserLoginData data;

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

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
