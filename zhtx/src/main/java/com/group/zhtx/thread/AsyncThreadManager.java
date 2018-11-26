package com.group.zhtx.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AsyncThreadManager {

    public static int threadNum;
    public static int priorityNum;
    public static int intervalTime;
    public static Map<Integer, AsyncThread> asyncThreadMap = new HashMap<>();

    public static void init(int threadNum, int priorityNum, int intervalTime) {
        AsyncThreadManager.threadNum = threadNum;
        AsyncThreadManager.priorityNum = priorityNum;
        AsyncThreadManager.intervalTime = intervalTime;

        for (int i = 0; i < threadNum; ++i) {
            AsyncThread thread = new AsyncThread(intervalTime, priorityNum);
            asyncThreadMap.put(i, thread);
        }

        //开启线程
        startAsyncThreads();
    }

    public static boolean checkThreadNumAndPriority(int threadNum, int priorityNum) {
        if (threadNum > AsyncThreadManager.threadNum || threadNum < 0) {
            return true;
        }
        if (priorityNum > AsyncThreadManager.priorityNum || priorityNum < 0) {
            return true;
        }
        return false;
    }

    public static boolean addCycle(IAsyncCycle cycle, int threadNum, int priorityNum) {

        if (cycle == null) {
            return false;
        }

        if (checkThreadNumAndPriority(threadNum, priorityNum)) return true;

        AsyncThread thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if (thread == null) {
            return false;
        }
        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if (handleData == null) {
            return false;
        }
        try {
            handleData.waitToAddCycles.put(cycle);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("AsyncThreadManager添加新Cycle失败");
            return false;
        }

    }

    public static boolean removeCycle(IAsyncCycle cycle, int threadNum, int priorityNum) {

        if (cycle == null) {
            return false;
        }

        if (checkThreadNumAndPriority(threadNum, priorityNum)) return true;

        AsyncThread thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if (thread == null) {
            return false;
        }
        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if (handleData == null) {
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

    public static boolean addHandle(IAsyncHandle handle, int threadNum, int priorityNum) {

        if (handle == null) {
            return false;
        }

        if (checkThreadNumAndPriority(threadNum, priorityNum)) return true;

        AsyncThread thread = AsyncThreadManager.asyncThreadMap.get(threadNum);

        if (thread == null) {
            return false;
        }

        AsyncPriorityHandleData handleData = thread.handleDataMap.get(priorityNum);

        if (handleData == null) {
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

    public static int[] getRandomThreadAndPriority() {
        int threadDegree = (int) (Math.random() * threadNum);
        int priorityDegree = (int) (Math.random() * priorityNum);
        int[] degree = new int[]{threadDegree, priorityDegree};
        return degree;
    }

    public static boolean startAsyncThreads() {
        for (int i = 0; i < asyncThreadMap.size(); i++) {
            AsyncThread thread = asyncThreadMap.get(i);
            thread.start();
        }
        return true;
    }
}
