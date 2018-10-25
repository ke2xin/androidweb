package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserCreateGroupC implements IMessage{

    private int operateId;
    private String uuid;
    private String groupName;
    private String groupHobby;
    private String groupDec;

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
