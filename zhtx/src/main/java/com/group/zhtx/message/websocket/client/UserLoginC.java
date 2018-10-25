package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserLoginC implements Serializable,IMessage {

    private int operateId;
    private String uuid;
    private String password;


    public UserLoginC(){}

    public UserLoginC(int operateId, String uuid, String password, String userPhone) {
        this.operateId = operateId;
        this.uuid = uuid;
        this.password = password;
    }

    public int getOperateCode() {
        return operateId;
    }

    public void setOperateCode(int operateCode) {
        this.operateId = operateCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
