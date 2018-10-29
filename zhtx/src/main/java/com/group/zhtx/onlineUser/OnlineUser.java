package com.group.zhtx.onlineUser;

import com.group.zhtx.message.websocket.service.groupMessage.GroupMessageData;
import com.group.zhtx.message.websocket.service.groupMessage.GroupMessageS;
import com.group.zhtx.model.Group;

import com.group.zhtx.model.Message;
import com.group.zhtx.repository.GroupUserRepository;
import com.group.zhtx.repository.MessageRepository;
import com.group.zhtx.thread.IAsyncCycle;
import com.group.zhtx.webSocket.WebSocket;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class OnlineUser implements IAsyncCycle{


    /*
        在线用户基本信息
     */
    private OnlineUserData userData;

    /*
        是否在线
     */
    private boolean isOnline;

    /*
        用户会话
     */
    private Session session;

    private MessageRepository messageRepository;

    private GroupUserRepository groupUserRepository;

    /*
        在线用户所在的线程和优先级
     */
    private int[] threadAndPrority;

    private LinkedBlockingDeque<Message> waitToSendMessage = new LinkedBlockingDeque<>();

    private ArrayList<Message> currentsendMessages = new ArrayList<>();

    public OnlineUser(OnlineUserData userData, Session session, GroupUserRepository groupUserRepository, MessageRepository messageRepository){
        this.userData = userData;
        this.session = session;
        this.messageRepository = messageRepository;
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    public void onAdd() throws Exception {
        //当用户添加时，给用户发送离线未接受到数据
        String userUuid = userData.getUuid();
        //获取用户所拥有的群组
        List<Group> groups = groupUserRepository.getGroupsByUserUuid(userUuid);
        Map<String,List<Message>> groupsMessage = new HashMap<>();

        for (Group group : groups){
            String groupUuid = group.getUuid();
            //获取当前群内成员最后接收到消息的时间
            Date groupUserReceiveTime = groupUserRepository.getGroupLastestTime(userUuid,groupUuid);

            //获取用户离开后未读取的消息
            List<Message> messages = messageRepository.getUnReadMessageByGroupUuidAndTime(groupUuid,groupUserReceiveTime);

            groupsMessage.put(groupUuid,messages);
        }

        //发送组消息
        sendGroupMessage(groupsMessage);
    }

    @Override
    public void onCycle() throws Exception {

        if(!isOnline)return;
        ArrayList<Message> messages = getCurrentSendMessages();

        if(messages.size() != 0){
            //分类各群消息
            Map<String,List<Message>> groupMessages = getSendGroupMessageMap(messages);
            //发送群消息
            sendGroupMessage(groupMessages);
        }
    }

    @Override
    public void onRemove() throws Exception {
        isOnline = false;
    }

    public void Clear(){
        userData = null;
        session = null;
        messageRepository = null;
        groupUserRepository = null;
        threadAndPrority = null;
        waitToSendMessage = null;
        currentsendMessages = null;
    }

    public OnlineUserData getData() {
        return userData;
    }

    public void setData(OnlineUserData userData) {
        this.userData = userData;
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
    public ArrayList<Message> getCurrentSendMessages(){
        currentsendMessages.clear();
        waitToSendMessage.drainTo(currentsendMessages);

        return currentsendMessages;
    }


    public void sendGroupMessage(Map<String,List<Message>> groupMessageMap){

        Object[] keys = groupMessageMap.keySet().toArray();
        GroupMessageS groupMessageS = new GroupMessageS();
        //设置发送组消息的操作码为23
        groupMessageS.setOperateId(23);


        for (Object key : keys) {
            int groupId = (int) key;
            List<Message> groupMessage = groupMessageMap.get(groupId);
            sendGroupMessage(groupMessageS, groupId, groupMessage);
        }

        WebSocket webSocket = new WebSocket(23,groupMessageS,null);

        sendMessage(webSocket);
    }

    public void sendMessage(WebSocket webSocket){

        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        发送组消息
     */
    public void sendGroupMessage(GroupMessageS groupMessageS, int groupId, List<Message> messagesList){

        GroupMessageData groupMessageData = new GroupMessageData();
        groupMessageData.setGroupUuid(groupId);
        groupMessageData.setMessages(messagesList);
        groupMessageS.addMessage(groupMessageData);

    }

    /*
        分类发给群组的消息集合
        key:GroupId
        value:群组消息集合
     */
    public Map<String,List<Message>> getSendGroupMessageMap(ArrayList<Message> messages){
        Map<String , List<Message>> messageMap = new HashMap<>();

        for (Message message : messages) {
            String groupUuid = message.getGroup().getUuid();

            if (!messageMap.containsKey(groupUuid)) {
                List<Message> messageList = new ArrayList<>();
                messageMap.put(groupUuid, messageList);

                messageList.add(message);
                continue;
            }

            List<Message> messageList = messageMap.get(groupUuid);
            messageList.add(message);
        }
        return messageMap;
    }

}

