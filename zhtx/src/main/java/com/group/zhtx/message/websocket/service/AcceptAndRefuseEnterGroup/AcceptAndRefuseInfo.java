package com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup;

public class AcceptAndRefuseInfo {
    private String request_user_uuid;
    private String group_uuid;

    public String getRequest_user_uuid() {
        return request_user_uuid;
    }

    public void setRequest_user_uuid(String request_user_uuid) {
        this.request_user_uuid = request_user_uuid;
    }

    public String getGroup_uuid() {
        return group_uuid;
    }

    public void setGroup_uuid(String group_uuid) {
        this.group_uuid = group_uuid;
    }
}
