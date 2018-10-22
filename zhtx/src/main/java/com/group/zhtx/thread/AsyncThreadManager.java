package com.group.zhtx.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AsyncThreadManager {

    public static int threadNum;
    public static int priorityNum;
    public static int intervalTime;
    public static Map<Integer,AsyncThread> asyncThreadMap = new HashMap<>();

    public static void init(int threadNum, int priorityNum, int intervalTime){
        AsyncThreadManager.threadNum = threadNum;
        AsyncThreadManager.priorityNum = priorityNum;
        AsyncThreadManager.intervalTime = intervalTime;

        for(int i = 0 ;i < threadNum; ++i){
            AsyncThread thread = new AsyncThread(intervalTime,priorityNum);
            asyncThreadMap.put(i, thread);
        }
    }

    public static boolean validThreadNumAndPriority(int threadNum, int priorityNum){
        if (threadNum >AsyncThreadManager.threadNum || threadNum < 0){
            return false;
        }
        if(priorityNum > AsyncThreadManager.priorityNum || priorityNum < 0){
            return false;
        }
        return true;
    }

    public static boolean addCycle(IAsyncCycle cycle,int threadNum, int priorityNum){

        if(cycle == null){
            return false;
        }

        if(!validThreadNumAndPriority(threadNum,priorityNum))return false;

        AsyncThread  thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if(thread == null){
            return false;
        }
        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if(handleData == null){
            return false;
        }
        try {
            handleData.waitToRemoveCycles.put(cycle);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("AsyncThreadManager添加新Cycle失败");
            return false;
        }

    }

    public static boolean removeCycle(IAsyncCycle cycle,int threadNum, int priorityNum){

        if(cycle == null){
            return false;
        }

        if(!validThreadNumAndPriority(threadNum,priorityNum))return false;

        AsyncThread  thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if(thread == null){
            return false;
        }
        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if(handleData == null){
            return false;
        }
        try {
            handleData.waitToRemoveCycles.put(cycle);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("AsyncThreadManager删除Cycle失败");
            return false;
        }
    }

    public static boolean addHandle(IAsyncHandle handle,int threadNum, int priorityNum){

        if(handle == null){
            return false;
        }

        if(!validThreadNumAndPriority(threadNum,priorityNum))return false;

        AsyncThread  thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if(thread == null){
            return false;
        }
        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if(handleData == null){
            return false;
        }
        try {
            handleData.waitToAddHandles.put(handle);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("AsyncThreadManager添加Handle失败");
            return false;
        }
    }

    public static int[] getRandomThreadAndPriority(){
        int threadDegree = new Random(threadNum).nextInt();
        int priorityDegree = new Random(priorityNum).nextInt();
        int[] degree = new int[]{threadDegree,priorityDegree};
        return  degree;
    }
}
