package com.group.zhtx.onlineUser;

import com.group.zhtx.thread.IAsyncCycle;
import org.hibernate.Session;

import java.util.concurrent.LinkedBlockingDeque;

public class OnlineUser implements IAsyncCycle{


    /*
        用户登录账户
     */
    private String uuid;

    /*
        用户名
     */
    private String userName;

    /*
        用户账户
     */
    private String userPortrait;

    /*
        用户头像
     */
    private boolean isOnline;

    /*
        用户会话
     */
    private Session session;

    /*
        在线用户所在的线程和优先级
     */
    private int[] threadAndPrority;

    @Override
    public void onAdd() throws Exception {

    }

    @Override
    public void onCycle() throws Exception {

    }

    @Override
    public void onRemove() throws Exception {

    }
}
