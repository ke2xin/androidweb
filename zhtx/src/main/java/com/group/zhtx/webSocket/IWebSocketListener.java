package com.group.zhtx.webSocket;

import java.util.HashMap;
import java.util.Map;

public interface IWebSocketListener {

    /*
        实现该接口可以进行websocket消息注册，并在接到消息进行回调方法
     */
    public Map<Integer, String> getWebSocketService() throws Exception;


}
