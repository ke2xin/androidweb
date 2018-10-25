package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserSaveGroupDataC implements IMessage {

    private int operateId;
    private String groupId;
    private String groupName;
    private String groupHobby;
    private String groupDec;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHobby() {
        return groupHobby;
    }

    public void setGroupHobby(String groupHobby) {
        this.groupHobby = groupHobby;
    }

    public String getGroupDec() {
        return groupDec;
    }

    public void setGroupDec(String groupDec) {
        this.groupDec = groupDec;
    }
}
