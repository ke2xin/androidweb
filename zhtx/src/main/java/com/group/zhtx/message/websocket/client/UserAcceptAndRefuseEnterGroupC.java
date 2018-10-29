package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserAcceptAndRefuseEnterGroupC implements Serializable, IMessage {
    private int operateId;
    private String request_user_uuid;
    private String send_user_uuid;
    private String group_uuid;
    private String result;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getRequest_user_uuid() {
        return request_user_uuid;
    }

    public void setRequest_user_uuid(String request_user_uuid) {
        this.request_user_uuid = request_user_uuid;
    }

    public String getSend_user_uuid() {
        return send_user_uuid;
    }

    public void setSend_user_uuid(String send_user_uuid) {
        this.send_user_uuid = send_user_uuid;
    }

    public String getGroup_uuid() {
        return group_uuid;
    }

    public void setGroup_uuid(String group_uuid) {
        this.group_uuid = group_uuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
