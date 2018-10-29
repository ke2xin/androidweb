package com.group.zhtx.message.websocket.service.groupNumberInfo;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class GroupNumberInfo {
    private String userName;
    private String userPortarit;
    private String userEmail;
    private String userSign;
    private String userPhone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPortarit() {
        return userPortarit;
    }

    public void setUserPortarit(String userPortarit) {
        this.userPortarit = WebSocketOperateUtil.Portrait_Url+userPortarit;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
