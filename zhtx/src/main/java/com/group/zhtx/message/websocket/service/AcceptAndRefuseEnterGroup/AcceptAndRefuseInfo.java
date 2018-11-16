package com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup;

public class AcceptAndRefuseInfo {
    private String requestUserUuid;
    private String groupUuid;

    public String getRequestUserUuid() {
        return requestUserUuid;
    }

    public void setRequestUserUuid(String requestUserUuid) {
        this.requestUserUuid = requestUserUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }
}
