package com.group.zhtx.webSocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;

public class WebSocketDeCoder implements Decoder.Binary<WebSocket> {


    @Override
    public WebSocket decode(ByteBuffer byteBuffer) throws DecodeException {
        System.out.println("我在解码");
        return WebSocketUtil.decodeJosn(byteBuffer);
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
