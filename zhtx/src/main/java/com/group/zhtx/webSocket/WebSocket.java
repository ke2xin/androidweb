package com.group.zhtx.webSocket;

import com.group.zhtx.message.IMessage;

import javax.websocket.Session;

public class WebSocket {
    /*
        WebSocket数据包，用于客户端消息的封装
     */
    private Integer operateId;
    private IMessage IMessage;
    private Session session;

    public WebSocket(Integer operateId, IMessage IMessage, Session session) {
        this.operateId = operateId;
        this.IMessage = IMessage;
        this.session = session;
    }

    public WebSocket(Integer operateId, IMessage IMessage) {
        this.operateId = operateId;
        this.IMessage = IMessage;
    }


    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public IMessage getIMessage() {
        return IMessage;
    }

    public void setIMessage(IMessage IMessage) {
        this.IMessage = IMessage;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void clear(){
        this.operateId = null;
        this.IMessage = null;
        this.session = null;
    }
}
