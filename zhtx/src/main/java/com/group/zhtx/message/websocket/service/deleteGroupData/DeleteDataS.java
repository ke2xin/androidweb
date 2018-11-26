package com.group.zhtx.message.websocket.service.deleteGroupData;

import com.group.zhtx.message.IMessage;
import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataMember;

import java.util.ArrayList;
import java.util.List;


public class DeleteDataS implements IMessage {
    private int operateId;
    private String status;
    private String delUuid;
    private List<UserGetGroupDataMember> numbers = new ArrayList<>();
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


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDelUuid() {
        return delUuid;
    }

    public void setDelUuid(String delUuid) {
        this.delUuid = delUuid;
    }

    public void addNumber(UserGetGroupDataMember number) {
        numbers.add(number);
    }
}
