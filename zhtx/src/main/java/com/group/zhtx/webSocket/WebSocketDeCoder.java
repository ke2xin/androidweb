package com.group.zhtx.webSocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.*;
import java.nio.ByteBuffer;

public class WebSocketDeCoder implements Decoder.Binary<WebSocket> {

    //这里投返回null会报错
    @Override
    public WebSocket decode(ByteBuffer byteBuffer) throws DecodeException {
        System.out.println("我在解码");
       // return new WebSocket(null,null,null);
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
