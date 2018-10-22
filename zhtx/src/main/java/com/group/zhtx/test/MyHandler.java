package com.group.zhtx.test;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {

    private static ConcurrentHashMap<String,WebSocketSession> clientMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clientMap.put(session.getId(),session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(session.getId()+"||"+message.getPayload());
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage(session.getId()+"||"+message.getPayload()));
        super.handleTextMessage(session, message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId()+"is closed");
        super.afterConnectionClosed(session, status);
    }

    public static ConcurrentHashMap<String, WebSocketSession> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(ConcurrentHashMap<String, WebSocketSession> clientMap) {
        MyHandler.clientMap = clientMap;
    }


}