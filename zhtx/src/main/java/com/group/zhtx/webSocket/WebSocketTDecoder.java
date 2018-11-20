package com.group.zhtx.webSocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class WebSocketTDecoder implements Decoder.Text<WebSocket> {

    @Override
    public WebSocket decode(String s) throws DecodeException {
        System.out.println("我根据字符串解码");
        return WebSocketUtil.decodeJson(s);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
