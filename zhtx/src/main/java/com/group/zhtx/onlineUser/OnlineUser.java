package com.group.zhtx.onlineUser;

import com.group.zhtx.model.Message;
import com.group.zhtx.thread.IAsyncCycle;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public LinkedBlockingDeque<Message> waitToSendMessage = new LinkedBlockingDeque<>();

    private ArrayList<Message> sendMessages = new ArrayList<>();

    public OnlineUser(String uuid, String userName, String userPortrait, Session session){
        this.uuid = uuid;
        this.userName = userName;
        this.userPortrait = userPortrait;
        this.session = session;
    }

    @Override
    public void onAdd() throws Exception {

    }

    @Override
    public void onCycle() throws Exception {

    }

    @Override
    public void onRemove() throws Exception {

    }

    public String getUuid() {
        return uuid;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int[] getThreadAndPrority() {
        return threadAndPrority;
    }

    public void setThreadAndPrority(int[] threadAndPrority) {
        this.threadAndPrority = threadAndPrority;
    }

    /*
        添加等待发送的消息
     */
    public boolean addWaitToSendMessage(Message message){

        try {
            waitToSendMessage.put(message);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
        获取需要发送的消息
     */
    public ArrayList<Message> getSendMessages(){
        sendMessages.clear();
        waitToSendMessage.drainTo(sendMessages);

        return sendMessages;
    }

    public Map<String,List<Message>> handleSendMessage(ArrayList<Message> messages){
        Map<String , List<Message>> messageMap = new HashMap<>();

         for(int i = 0; i < messages.size(); ++i){
             Message message = messages.get(i);
             String groupUuid = message.getGroup().getUuid();

             if(!messageMap.containsKey(groupUuid)){
                 List<Message> messageList = new ArrayList<>();
                 messageMap.put(groupUuid,messageList);

                 messageList.add(message);
                 continue;
             }

             List<Message> messageList = messageMap.get(groupUuid);
             messageList.add(message);
         }


        return null;
    }
}

