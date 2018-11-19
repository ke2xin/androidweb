package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserLocationInfoC implements Serializable, IMessage {
    private int operateId;
    private String group_id;

    public UserLocationInfoC() {
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public UserLocationInfoC(int operateId, String groupId) {
=======
    public UserLocationInfoC(int operateId, String group_id) {
>>>>>>> parent of accf4a9... 首页
=======
    public UserLocationInfoC(int operateId, String group_id) {
>>>>>>> parent of d069db5... Merge branch 'master' of https://github.com/ke2xin/androidweb
        this.operateId = operateId;
        this.group_id = group_id;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
