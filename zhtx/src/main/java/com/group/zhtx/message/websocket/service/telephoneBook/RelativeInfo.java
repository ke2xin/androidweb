package com.group.zhtx.message.websocket.service.telephoneBook;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class RelativeInfo {
    private String userName;
    private String userPortarit;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
