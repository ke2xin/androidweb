package com.group.zhtx.thread;

import com.group.zhtx.webSocket.WebSocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncThread extends Thread {

    private int intervalTime;
    public Map<Integer, AsyncPriorityHandleData> handleDataMap = new HashMap<>();


    public AsyncThread(int intervalTime, int threadPriorityNum){
        this.intervalTime = intervalTime;

        for (int i = 0; i < threadPriorityNum ; ++i){
            AsyncPriorityHandleData handleData = new AsyncPriorityHandleData();
            handleDataMap.put(i,handleData);
        }
    }

    @Override
    public void run(){

        while (true){
            long startTime = System.currentTimeMillis();

            for (int i = 0; i <handleDataMap.size(); ++i){
                AsyncPriorityHandleData handleData = handleDataMap.get(i);

                //处理当前队列的循环
                ArrayList<IAsyncCycle> cycles = handleData.getCurrentCycles();
                for(int l = 0; l <cycles.size(); ++l){
                    IAsyncCycle cycle = cycles.get(l);
                    try {
                        cycle.onCycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(l + "Cycle循环出现异常");
                    }
                }

                //处理当前队列消息
                ArrayList<IAsyncHandle> handles = handleData.getCurrentHandles();
                for (int l = 0; l < handles.size(); ++l){
                    IAsyncHandle handle = handles.get(l);

                    Method method = handle.getMethod();
                    WebSocket webSocket = (WebSocket) handle.getPacket();
                    try {
                        method.invoke(handle.getInstance(),webSocket);
                        webSocket.clear();
                    } catch (Exception e) {
                        System.out.println(l + "Method产生异常");
                        e.printStackTrace();
                    }
                }



                //将Cycle添加循环队列
                ArrayList<IAsyncCycle> addCycles = handleData.getAddCycles();
                for(int l = 0; l < addCycles.size(); l++){

                    IAsyncCycle cycle = addCycles.get(l);

                    try {
                        handleData.addCycle(cycle);
                    } catch (Exception e) {
                        System.out.println(l + "addCycle失败");
                    }
                }

                //将Cycle移除出队列
                ArrayList<IAsyncCycle> removeCycles = handleData.getRemoveCycles();
                for(int l = 0; l < removeCycles.size(); l++){

                    IAsyncCycle cycle = removeCycles.get(l);

                    try {
                        handleData.removeCycle(cycle);
                    } catch (Exception e) {
                        System.out.println(l + "removeCycle失败");
                    }
                }
            }

            long endTime = System.currentTimeMillis();

            if((endTime - startTime) < intervalTime){
                long times = intervalTime - (endTime-startTime);
                try {
                    Thread.sleep(times);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
