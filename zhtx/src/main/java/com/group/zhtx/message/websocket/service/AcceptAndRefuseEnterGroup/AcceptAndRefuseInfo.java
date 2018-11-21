package com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup;

public class AcceptAndRefuseInfo {
    private String requestUserUuid;
    private String groupUuid;
    private int noticeId;

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

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }
}
