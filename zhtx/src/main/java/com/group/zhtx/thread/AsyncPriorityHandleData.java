package com.group.zhtx.thread;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncPriorityHandleData {


    /*
        等待添加的消息包
     */
    public LinkedBlockingQueue<IAsyncHandle> waitToAddHandles = new LinkedBlockingQueue<>();

    /*
        当前要处理的消息
     */
    private ArrayList<IAsyncHandle> currentHandles = new ArrayList<>();

    /*
        当前要循环处理的对象
     */
    private ArrayList<IAsyncCycle> currentCycles = new ArrayList<>();

    /*
        等待加入循环处理的对象
     */
    public LinkedBlockingQueue<IAsyncCycle> waitToAddCycles = new LinkedBlockingQueue<>();

    /*
        辅助
     */
    private ArrayList<IAsyncCycle> addCycles  = new ArrayList<>();

    /*
        等待移除循环处理的对象
     */
    public LinkedBlockingQueue<IAsyncCycle> waitToRemoveCycles = new LinkedBlockingQueue<>();

    /*
        辅助
     */
    private ArrayList<IAsyncCycle> removeCycles = new ArrayList<>();


    public ArrayList<IAsyncHandle> getCurrentHandles(){

        currentHandles.clear();
        waitToAddHandles.drainTo(currentHandles);
        return currentHandles;
    }

    public ArrayList<IAsyncCycle> getCurrentCycles(){

        return currentCycles;
    }

    public ArrayList<IAsyncCycle> getAddCycles() {

        addCycles.clear();
        waitToAddCycles.drainTo(addCycles);
        return addCycles;
    }

    public ArrayList<IAsyncCycle> getRemoveCycles() {

        removeCycles.clear();
        waitToRemoveCycles.drainTo(removeCycles);
        return removeCycles;
    }

    public void addCycle(IAsyncCycle cycle) throws Exception{
        if(cycle == null) throw new Exception("添加Cycle失败");
        currentCycles.add(cycle);

    }

    public void removeCycle(IAsyncCycle cycle) throws Exception{
        if(cycle == null) throw new Exception("移除Cycle失败");
        currentCycles.remove(cycle);

    }
}
