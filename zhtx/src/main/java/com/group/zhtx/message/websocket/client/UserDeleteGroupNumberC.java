package com.group.zhtx.message.websocket.client;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;

public class UserDeleteGroupNumberC implements Serializable, IMessage {
    private int operateId;
    private String group_id;
    private String uuid;
    private String del_uuid;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDel_uuid() {
        return del_uuid;
    }

    public void setDel_uuid(String del_uuid) {
        this.del_uuid = del_uuid;
    }
}
