package com.group.zhtx.message.websocket.service.createGroupMessage;

import com.group.zhtx.message.IMessage;
import com.group.zhtx.model.Group;

import java.util.ArrayList;
import java.util.List;

public class UserCreateGroupS implements IMessage {

    private int operateId;
    private String status;
    private String information;

    private List<UserCreateGroup> groups = new ArrayList<>();

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public List<UserCreateGroup> getGroups() {
        return groups;
    }

    public void addUserCreateGroup(UserCreateGroup userCreateGroup) {
        groups.add(userCreateGroup);
    }

    public void setGroups(List<UserCreateGroup> groups) {
        this.groups = groups;
    }
}
