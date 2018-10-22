package com.group.zhtx.webSocket;

public class WebSocket {
    /*
        WebSocket数据包，用于客户端消息的封装
     */
    private Integer operateId;
    private String data;
    private Object otherData;

    public WebSocket(Integer operateId, String data, Object otherData) {
        this.operateId = operateId;
        this.data = data;
        this.otherData = otherData;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public String getData() {
        return data;
    }

    public Object getOtherData() {
        return otherData;
    }

    public void clear(){
        this.operateId = null;
        this.data = null;
        this.otherData = null;
    }
}
