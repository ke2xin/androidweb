package com.group.zhtx.message.websocket.service.searchData;

import com.group.zhtx.message.IMessage;

import java.util.ArrayList;
import java.util.List;

public class SearchDataS implements IMessage {
   private int operateId;
   private String status;
   private List<SearchDataInfo> dataInfoList=new ArrayList<>();
   private String information;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SearchDataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<SearchDataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
