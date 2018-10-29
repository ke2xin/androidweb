package com.group.zhtx.webSocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WebSocketTDecoder implements Decoder.Text<WebSocket> {

    @Override
    public WebSocket decode(String s) throws DecodeException {
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
