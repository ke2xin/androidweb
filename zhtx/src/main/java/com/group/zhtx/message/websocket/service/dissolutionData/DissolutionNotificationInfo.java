package com.group.zhtx.message.websocket.service.dissolutionData;

import java.util.Date;

public class DissolutionNotificationInfo {
    private String uuid;//通知者
    private String noticeContent;//通知内容
    private Date time;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
