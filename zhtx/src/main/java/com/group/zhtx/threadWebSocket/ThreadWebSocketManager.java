package com.group.zhtx.threadWebSocket;


import com.example.jpa.demo.thread.AsyncThreadManager;
import com.example.jpa.demo.thread.ThreadHandle;
import com.example.jpa.demo.webSocket.WebSocket;
import com.example.jpa.demo.webSocket.WebSocketManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
    用于将客户端发过来的消息进行消息封装，分发
 */
public class ThreadWebSocketManager {

    /*
        根据登记的等级进行消息分发，没有登记的就进行随机分发给线程优先级进行处理
     */
    public static Map<Integer,int[]> operateDispatchDegree = new HashMap<>();


    public static boolean dispatchWebSocket(WebSocket webSocket){


       Integer operateId = webSocket.getOperateId();
       int[] threadDegree;
       if(operateDispatchDegree.containsKey(operateId)){
           threadDegree = operateDispatchDegree.get(operateId);

           if(threadDegree.length < 2){
               threadDegree = AsyncThreadManager.getRandomThreadAndPriority();
           }
       }else {
            threadDegree = AsyncThreadManager.getRandomThreadAndPriority();
       }
        Method method = WebSocketManager.getMethodByOperateId(operateId);
       if(method == null){
           return false;
       }
       Object instance = WebSocketManager.getInstaceByMethod(method);
       if(instance == null){
           return false;
       }

        ThreadHandle handle = new ThreadHandle(webSocket, method, instance);
        AsyncThreadManager.addHandle(handle,threadDegree[0],threadDegree[1]);
        return true;
    }
}
