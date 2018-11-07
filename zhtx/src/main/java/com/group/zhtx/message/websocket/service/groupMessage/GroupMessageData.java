package com.group.zhtx.message.websocket.service.groupMessage;

import com.group.zhtx.model.Message;

import java.util.ArrayList;
import java.util.List;

public class GroupMessageData {


    private int groupUuid;

    private List<Message> messages;


    public int getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(int groupUuid) {
        this.groupUuid = groupUuid;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message){
        messages.add(message);
    }
}

