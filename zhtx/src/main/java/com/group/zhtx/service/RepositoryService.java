package com.group.zhtx.service;


import com.group.zhtx.message.client.RegisterC;
import com.group.zhtx.repository.UserRepository;
import com.group.zhtx.util.common.WebSocketOperateUtil;
import com.group.zhtx.webSocket.IWebSocketListener;
import com.group.zhtx.webSocket.WebSocket;
import com.group.zhtx.webSocket.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/*
    操作的实现
 */
@Service
@Transactional(readOnly=true)
public class RepositoryService implements IRepositoryService,IWebSocketListener {

    private static Logger logger = LoggerFactory.getLogger(RepositoryService.class);

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @PostConstruct
    public void initMethod() throws Exception {
        WebSocketManager.addWebSocketListener(this);
    }

    @Override
    public Map<Integer, String> getWebSocketService() throws Exception {
        Map<Integer,String> map = new HashMap<>();
        map.put(WebSocketOperateUtil.User_Login_C,"registerUser");

        return map;
    }


    public void registerUser(WebSocket webSocket){
        logger.info("webSocketOerateId:" + webSocket.getOperateId());
        RegisterC registerC = (RegisterC) webSocket.getMessage();
        logger.info("webSocketMessage:" + registerC.getUuid());
        logger.info("webSocketMessage:" + registerC.getUserPhone());
        logger.info("webSocketMessage:" + registerC.getPassword());
    }



}
