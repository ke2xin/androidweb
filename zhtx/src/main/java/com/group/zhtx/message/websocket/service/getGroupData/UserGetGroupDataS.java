package com.group.zhtx.message.websocket.service.getGroupData;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class UserGetGroupDataS implements IMessage {

    private int operateId;
    private String groupName;
    private String groupPortrait;
    private String groupNumber;
    private String status;
    private String groupAnoun;
    private List<UserGetGroupDataMember> members = new ArrayList<>();

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

    public String getGroupAnoun() {
        return groupAnoun;
    }

    public void setGroupAnoun(String groupAnoun) {
        this.groupAnoun = groupAnoun;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
