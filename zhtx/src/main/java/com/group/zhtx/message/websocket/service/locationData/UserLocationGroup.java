package com.group.zhtx.message.websocket.service.locationData;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class UserLocationGroup {
    private String userName;
    private String userPortarit;
<<<<<<< HEAD
    private String userLocationLongitude;
    private String userLocationLatitude;
    private long userLocationTime;
=======
    private String user_location_longitude;
    private String user_location_latitude;
    private String user_location_corner;
    private String user_location_time;
>>>>>>> parent of accf4a9... 首页

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

    public String getUser_location_longitude() {
        return user_location_longitude;
    }

    public void setUser_location_longitude(String user_location_longitude) {
        this.user_location_longitude = user_location_longitude;
    }

    public String getUser_location_latitude() {
        return user_location_latitude;
    }

    public void setUser_location_latitude(String user_location_latitude) {
        this.user_location_latitude = user_location_latitude;
    }

<<<<<<< HEAD
    public long getUserLocationTime() {
        return userLocationTime;
    }

    public void setUserLocationTime(long userLocationTime) {
        this.userLocationTime = userLocationTime;
=======
    public String getUser_location_corner() {
        return user_location_corner;
    }

    public void setUser_location_corner(String user_location_corner) {
        this.user_location_corner = user_location_corner;
    }

    public String getUser_location_time() {
        return user_location_time;
    }

    public void setUser_location_time(String user_location_time) {
        this.user_location_time = user_location_time;
>>>>>>> parent of accf4a9... 首页
    }
}
