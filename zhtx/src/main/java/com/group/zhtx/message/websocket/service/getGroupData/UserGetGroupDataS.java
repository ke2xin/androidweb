package com.group.zhtx.message.websocket.service.getGroupData;

import com.group.zhtx.message.IMessage;

import java.util.List;

public class UserGetGroupDataS implements IMessage {

    private int operateId;
    private String groupName;
    private String groupPortrait;
    private String groupNumber;
    private List<UserGetGroupDataMember> members;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<UserGetGroupDataMember> getMembers() {
        return members;
    }

    public void setMembers(List<UserGetGroupDataMember> members) {
        this.members = members;
    }

    public void addMember(UserGetGroupDataMember member){
        this.members.add(member);
    }
}
