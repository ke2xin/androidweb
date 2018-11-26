package com.group.zhtx.thread;

public interface IAsyncCycle {

    void onAdd() throws Exception;

    void onCycle() throws Exception;

    void onRemove() throws Exception;
}
