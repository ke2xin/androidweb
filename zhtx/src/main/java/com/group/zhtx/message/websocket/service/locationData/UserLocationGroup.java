package com.group.zhtx.message.websocket.service.locationData;


public class UserLocationGroup {
    private String userName;
    private String userId;
    private String userPortrait;
    private String userLocationLongitude;
    private String userLocationLatitude;
    private String userLocationCorner;
    private String userLocationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
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

    public String getUserLocationCorner() {
        return userLocationCorner;
    }

    public void setUserLocationCorner(String userLocationCorner) {
        this.userLocationCorner = userLocationCorner;
    }

    public String getUserLocationTime() {
        return userLocationTime;
    }

    public void setUserLocationTime(String userLocationTime) {
        this.userLocationTime = userLocationTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
