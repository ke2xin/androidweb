package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserPersonalInfoC implements IMessage {
    private int operateId;
    private String uuid;
    private String uuid_pic;
    private String user_name;
    private String user_qianming;
    private String user_phone;
    private String user_email;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid_pic() {
        return uuid_pic;
    }

    public void setUuid_pic(String uuid_pic) {
        this.uuid_pic = uuid_pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_qianming() {
        return user_qianming;
    }

    public void setUser_qianming(String user_qianming) {
        this.user_qianming = user_qianming;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
