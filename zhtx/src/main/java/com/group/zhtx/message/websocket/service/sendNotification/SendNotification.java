package com.group.zhtx.message.websocket.service.sendNotification;

public class SendNotification {

    private String requsetUserUuid;
    private String sendUserUuid;
    private String groupUuid;
    private String content;
    private String sendUserPortrait;

    public String getRequsetUserUuid() {
        return requsetUserUuid;
    }

    public void setRequsetUserUuid(String requsetUserUuid) {
        this.requsetUserUuid = requsetUserUuid;
    }

    public String getSendUserUuid() {
        return sendUserUuid;
    }

    public void setSendUserUuid(String sendUserUuid) {
        this.sendUserUuid = sendUserUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendUserPortrait() {
        return sendUserPortrait;
    }

    public void setSendUserPortrait(String sendUserPortrait) {
        this.sendUserPortrait = sendUserPortrait;
    }
}
