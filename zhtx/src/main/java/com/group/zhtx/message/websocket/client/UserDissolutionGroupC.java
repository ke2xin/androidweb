package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;
import com.group.zhtx.message.websocket.service.createGroupMessage.UserCreateGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDissolutionGroupC implements Serializable, IMessage {
    private int operateId;
    private String uuid;
    private String groupId;


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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
