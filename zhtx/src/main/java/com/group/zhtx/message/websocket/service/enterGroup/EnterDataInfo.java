package com.group.zhtx.message.websocket.service.enterGroup;


public class EnterDataInfo {
    private String userPortrait;
    private String username;
    private String userMessage;
    private String userSendTime;

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserSendTime() {
        return userSendTime;
    }

    public void setUserSendTime(String userSendTime) {
        this.userSendTime = userSendTime;
    }
}
