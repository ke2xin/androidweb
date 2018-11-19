package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserAcceptAndRefuseEnterGroupC implements Serializable, IMessage {
    private int operateId;
    private String requestUserUuid;
    private String sendUserUuid;
    private String groupUuid;
    private String result;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getRequestUserUuid() {
        return requestUserUuid;
    }

    public void setRequestUserUuid(String requestUserUuid) {
        this.requestUserUuid = requestUserUuid;
    }

    public String getSendUserUuid() {
        return sendUserUuid;
    }

    public void setSendUserUuid(String sendUserUuid) {
        this.sendUserUuid = sendUserUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
