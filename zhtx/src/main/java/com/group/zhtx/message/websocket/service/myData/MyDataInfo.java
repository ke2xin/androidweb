package com.group.zhtx.message.websocket.service.myData;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class MyDataInfo {
    private String user_portrait;
    private String user_name;
    private String user_sign;

    public String getUser_portrait() {
        return user_portrait;
    }

    public void setUser_portrait(String user_portrait) {
        this.user_portrait = WebSocketOperateUtil.Portrait_Url+user_portrait;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }
}
