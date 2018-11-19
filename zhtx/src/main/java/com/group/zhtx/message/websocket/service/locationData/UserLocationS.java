package com.group.zhtx.message.websocket.service.locationData;


import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class UserLocationS implements IMessage {
    private String operateId;
    private String status;
    private List<UserLocationGroup>data=new ArrayList<>();
<<<<<<< HEAD
    private String information;

    public int getOperateId() {
=======
    public String getOperateId() {
>>>>>>> parent of accf4a9... 首页
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

    public List<UserLocationGroup> getData() {
        return data;
    }

    public void setData(List<UserLocationGroup> data) {
        this.data = data;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
