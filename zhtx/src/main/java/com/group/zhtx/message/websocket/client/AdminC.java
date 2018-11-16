package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class AdminC implements IMessage {
    private String loginName;
    private String loginPassword;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
