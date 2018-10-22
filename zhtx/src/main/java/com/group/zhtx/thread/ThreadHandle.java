package com.group.zhtx.thread;

import java.lang.reflect.Method;

public class ThreadHandle implements IAsyncHandle{


    /*
        消息包数据
     */
    private Object packet;
    /*
        处理消息包方法
     */
    private Method method;

    /*
        处理消息包对象实体
     */
    private Object instance;


    public ThreadHandle(Object packet, Method method, Object instance){
        this.packet = packet;
        this.method = method;
        this.instance = instance;
    }

    @Override
    public Object getPacket() {
        return null;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object getInstance() {
        return null;
    }
}
