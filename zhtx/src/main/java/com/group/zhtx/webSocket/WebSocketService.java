package com.group.zhtx.webSocket;


import com.group.zhtx.threadWebSocket.ThreadWebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws",decoders = {WebSocketDeCoder.class,WebSocketTDecoder.class},encoders = {WebSocketEncoder.class})
@Component
public class WebSocketService {

    private static Logger logger  = LoggerFactory.getLogger(WebSocketService.class);

    @OnOpen
    public void onOpen(Session session){

    }

    @OnMessage
    public void onMessage(Session session,WebSocket webSocket){

        if(webSocket == null){
            logger.error("WebSocket为null");
            return;
        }
        if (webSocket.getIMessage() == null){

            return;
        }

        webSocket.setSession(session);
        System.out.println("session中id:"+session.getId());
        ThreadWebSocketManager.dispatchWebSocket(webSocket);
    }


    @OnClose
    public void onClose(Session session){

    }
}
