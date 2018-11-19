package com.group.zhtx.message.websocket.service.locationData;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class UserLocationGroup {
    private String userName;
    private String userPortarit;
    private String userLocationLongitude;
    private String userLocationLatitude;
    private long userLocationTime;

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

    public String getUserLocationLongitude() {
        return userLocationLongitude;
    }

    public void setUserLocationLongitude(String userLocationLongitude) {
        this.userLocationLongitude = userLocationLongitude;
    }

    public String getUserLocationLatitude() {
        return userLocationLatitude;
    }

    public void setUserLocationLatitude(String userLocationLatitude) {
        this.userLocationLatitude = userLocationLatitude;
    }

    public long getUserLocationTime() {
        return userLocationTime;
    }

    public void setUserLocationTime(long userLocationTime) {
        this.userLocationTime = userLocationTime;
    }
}
