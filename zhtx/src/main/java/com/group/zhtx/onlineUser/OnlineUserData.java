package com.group.zhtx.onlineUser;

public class OnlineUserData {

    /*
        用户登录账户
     */
    private String uuid;

    /*
        用户电话号码
     */
    private String phone;

    /*
        用户名
     */
    private String userName;

    /*
        用户头像
     */
    private String userPortrait;

    public OnlineUserData(String uuid, String userName, String userPortrait, String phone) {
        this.uuid = uuid;
        this.userName = userName;
        this.userPortrait = userPortrait;
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public String getUserPhone() {
        return phone;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public void setUserPhone(String userPhone) {
        this.phone = userPhone;
    }
}
