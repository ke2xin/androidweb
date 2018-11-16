package com.group.zhtx.message.websocket.service.groupMessage;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class GroupMessageS implements IMessage {

    /*
        操作Id
     */
    private int operateId;

    private String status;
    /*
        返回的组消息
     */
    private List<GroupMessageData> data = new ArrayList<>();

    /*
        返回消息的描述
     */
    private String description;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public List<GroupMessageData> getData() {
        return data;
    }

    public void setData(List<GroupMessageData> data) {
        this.data = data;
    }

    public void addMessage(GroupMessageData message) {
        data.add(message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
