package com.group.zhtx.message.websocket.service.sendNotification;

public class SendNotification {
    private String sendUuid;
    private String userUuid;
    private String sendUserName;
    private String noticeContent;
    private long noticeTime;
    private String groupName;
    private String groupPortrait;
    private int status;
    private String groupId;
    private int noticeId;
    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSendUuid() {
        return sendUuid;
    }

    public void setSendUuid(String sendUuid) {
        this.sendUuid = sendUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getGroupPortrait() {
        return groupPortrait;
    }

    public void setGroupPortrait(String groupPortrait) {
        this.groupPortrait = groupPortrait;
    }

    public long getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(long noticeTime) {
        this.noticeTime = noticeTime;
    }
}
