package com.group.zhtx.message.client;

import com.group.zhtx.message.Message;

import java.io.Serializable;

public class RegisterC implements Serializable,Message{

    private int operateCode;
    private String uuid;
    private String password;
    private String userPhone;


    public RegisterC(){}

    public RegisterC(int operateCode, String uuid, String password, String userPhone) {
        this.operateCode = operateCode;
        this.uuid = uuid;
        this.password = password;
        this.userPhone = userPhone;
    }

    public int getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(int operateCode) {
        this.operateCode = operateCode;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
