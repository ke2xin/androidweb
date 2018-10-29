package com.group.zhtx.webSocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WebSocketEncoder implements Encoder.Text<WebSocket> {


    @Override
    public String encode(WebSocket webSocket) throws EncodeException {
        System.out.println("我在编码");
        return WebSocketUtil.encodeJson(webSocket);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
