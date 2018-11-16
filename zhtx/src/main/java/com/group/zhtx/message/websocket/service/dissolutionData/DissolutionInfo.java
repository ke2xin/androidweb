package com.group.zhtx.message.websocket.service.dissolutionData;

public class DissolutionInfo {
    private String groupName;
    private String groupNumber;
    private String groupPortrait;
    private String lastestGroupUser;
    private String lastGroupNumberName;
    private long lastGroupSendTime;
    private String lastestGroupMessage;
    private int groupMessageCount;
    private int groupRole;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    public String getLastestGroupUser() {
        return lastestGroupUser;
    }

    public void setLastestGroupUser(String lastestGroupUser) {
        this.lastestGroupUser = lastestGroupUser;
    }

    public String getLastGroupNumberName() {
        return lastGroupNumberName;
    }

    public void setLastGroupNumberName(String lastGroupNumberName) {
        this.lastGroupNumberName = lastGroupNumberName;
    }

    public long getLastGroupSendTime() {
        return lastGroupSendTime;
    }

    public void setLastGroupSendTime(long lastGroupSendTime) {
        this.lastGroupSendTime = lastGroupSendTime;
    }

    public String getLastestGroupMessage() {
        return lastestGroupMessage;
    }

    public void setLastestGroupMessage(String lastestGroupMessage) {
        this.lastestGroupMessage = lastestGroupMessage;
    }

    public int getGroupMessageCount() {
        return groupMessageCount;
    }

    public void setGroupMessageCount(int groupMessageCount) {
        this.groupMessageCount = groupMessageCount;
    }

    public int getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(int groupRole) {
        this.groupRole = groupRole;
    }
}
