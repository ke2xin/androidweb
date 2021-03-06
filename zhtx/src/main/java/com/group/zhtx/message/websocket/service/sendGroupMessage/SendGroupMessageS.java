package com.group.zhtx.message.websocket.service.sendGroupMessage;

import com.group.zhtx.message.IMessage;

public class SendGroupMessageS implements IMessage {

    private int operateId;
    private String status;
    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
