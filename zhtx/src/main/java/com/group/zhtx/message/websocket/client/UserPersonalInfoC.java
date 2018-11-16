package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

public class UserPersonalInfoC implements IMessage {
    private int operateId;
    private String uuid;
    private String uuidPic;
    private String userName;
    private String userQianming;
    private String userPhone;
    private String userEmail;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidPic() {
        return uuidPic;
    }

    public void setUuidPic(String uuidPic) {
        this.uuidPic = uuidPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserQianming() {
        return userQianming;
    }

    public void setUserQianming(String userQianming) {
        this.userQianming = userQianming;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
