package com.group.zhtx.message.websocket.service.groupMessage;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;

public class GroupMessageS implements IMessage {

    /*
        操作Id
     */
    private int operateId;

    /*
        返回的组消息
     */
    private ArrayList<GroupMessage> data;

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

    public ArrayList<GroupMessage> getData() {
        return data;
    }

    public void setData(ArrayList<GroupMessage> data) {
        this.data = data;
    }

    public void addMessage(GroupMessage message) {
        data.add(message);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
