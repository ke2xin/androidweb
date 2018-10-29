package com.group.zhtx.message.websocket.service.searchData;

import com.group.zhtx.util.common.WebSocketOperateUtil;

public class SearchDataInfo {
    private String group_desc;
    private String group_name;
    private String group_portarit;
    private String group_uuid;

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_portarit() {
        return group_portarit;
    }

    public void setGroup_portarit(String group_portarit) {
        this.group_portarit = WebSocketOperateUtil.Portrait_Url+group_portarit;
    }

    public String getGroup_uuid() {
        return group_uuid;
    }

    public void setGroup_uuid(String group_uuid) {
        this.group_uuid = group_uuid;
    }
}
