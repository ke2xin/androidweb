package com.group.zhtx.message.websocket.service.telephoneBook;

import com.group.zhtx.message.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelativeBookS implements Serializable, IMessage {
    private String operateId;
    private String status;
    private List<RelativeInfo> data=new ArrayList<>();

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RelativeInfo> getData() {
        return data;
    }

    public void setData(List<RelativeInfo> data) {
        this.data = data;
    }
}
