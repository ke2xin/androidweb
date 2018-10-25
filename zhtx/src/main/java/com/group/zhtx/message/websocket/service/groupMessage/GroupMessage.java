package com.group.zhtx.message.websocket.service.groupMessage;

import com.group.zhtx.model.Message;
import java.util.List;

public class GroupMessage {


    private int groupUuid;

    private List<Message> data;


    public int getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(int groupUuid) {
        this.groupUuid = groupUuid;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    public void addMessage(Message message){
        data.add(message);
    }
}

