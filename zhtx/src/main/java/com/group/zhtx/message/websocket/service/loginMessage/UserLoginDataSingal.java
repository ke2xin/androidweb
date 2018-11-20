package com.group.zhtx.message.websocket.service.loginMessage;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class UserLoginDataSingal {

    private String userPortrait;
    private String userName;
    private String userSign;
    private String userEmail;
    private String userPhone;


    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = WebSocketOperateUtil.Portrait_Url + userPortrait;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
