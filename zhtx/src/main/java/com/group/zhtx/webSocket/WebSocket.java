package com.group.zhtx.webSocket;

import com.group.zhtx.message.Message;

import javax.websocket.Session;

public class WebSocket {
    /*
        WebSocket数据包，用于客户端消息的封装
     */
    private Integer operateId;
    private Message message;
    private Session session;

    public WebSocket(Integer operateId, Message message, Session session) {
        this.operateId = operateId;
        this.message = message;
        this.session = session;
    }

    public WebSocket(Integer operateId, Message message) {
        this.operateId = operateId;
        this.message = message;
    }


    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void clear(){
        this.operateId = null;
        this.message = null;
        this.session = null;
    }
}
