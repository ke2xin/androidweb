package com.group.zhtx.webSocket;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
    该类作用是根据用户的操作，获取实体，提供回调方法
 */
public class WebSocketManager {

    private static Map<Integer, Method> wsMethodsMap = new HashMap<>();
    private static Map<Method, Object> wsInstanceMap = new HashMap<>();


    public static boolean addWebSocketListener(IWebSocketListener webSocketListener) throws Exception {
        System.out.println("这是WebSocketManager的方法：addWebSocketListener");
        Map<Integer, String> maps = webSocketListener.getWebSocketService();

        if (maps == null) return false;

        if (maps.size() == 0) return false;

        Object[] keys = maps.keySet().toArray();
        System.out.println("这是key，也就是操作码" + maps.keySet());

        for (int i = 0; i < keys.length; ++i) {
            Integer operateId = (Integer) keys[i];

            //每个操作只对应一个处理方法
            if (wsMethodsMap.containsKey(operateId)) {
                System.out.println(webSocketListener.getClass().toString() + "getWebSocketService()" + "第" + i + "个已经存在了方法");
                continue;
            }

            Method method = webSocketListener.getClass().getMethod(maps.get(operateId), WebSocket.class);
            System.out.println("这是websocketmanager的方法" + method.getName() + "   " + maps.get(operateId));
            wsMethodsMap.put(operateId, method);

            //每个处理方法只对应一个实体
            if (wsInstanceMap.containsKey(method)) {
                System.out.println(webSocketListener.getClass().toString() + "         " + "第" + i + "个已经存在了实体");
                continue;
            }

            wsInstanceMap.put(method, webSocketListener);

        }
        return true;
    }

    public static Method getMethodByOperateId(Integer operateId) {

        if (wsMethodsMap.containsKey(operateId)) {
            System.out.println("根据id来判断是否存在该方法，并返回");
            return wsMethodsMap.get(operateId);
        }
        return null;
    }

    public static Object getInstaceByMethod(Method method) {
        if (wsInstanceMap.containsKey(method)) {
            return wsInstanceMap.get(method);
        }
        return null;
    }
}
