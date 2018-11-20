package com.group.zhtx.onlineUser;

import com.group.zhtx.message.websocket.service.groupMessage.GroupMessageData;
import com.group.zhtx.message.websocket.service.groupMessage.GroupMessageS;
import com.group.zhtx.message.websocket.service.sendNotification.SendNotification;
import com.group.zhtx.message.websocket.service.sendNotification.SendNotificationS;
import com.group.zhtx.message.websocket.service.sendUserLocation.SendGroupLocationData;
import com.group.zhtx.message.websocket.service.sendUserLocation.SendGroupLocationS;
import com.group.zhtx.model.*;

import com.group.zhtx.repository.GroupRepository;
import com.group.zhtx.repository.GroupUserRepository;
import com.group.zhtx.repository.MessageRepository;
import com.group.zhtx.repository.NotificationRepository;
import com.group.zhtx.thread.IAsyncCycle;
import com.group.zhtx.util.common.WebSocketOperateUtil;
import com.group.zhtx.webSocket.WebSocket;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;


public class OnlineUser implements IAsyncCycle{

    private static Logger logger = LoggerFactory.getLogger(OnlineUser.class);
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

    private NotificationRepository notificationRepository;

    //群存储库
    private GroupRepository groupRepository;

    /*
        在线用户所在的线程和优先级
     */
    private int[] threadAndPrority;

    private LinkedBlockingDeque<Message> waitToSendMessage = new LinkedBlockingDeque<>();

    private ArrayList<Message> currentsendMessages = new ArrayList<>();

    //等待发送位置信息
    private LinkedBlockingDeque<Map<String,UserGps>>waitToSendLocationInfo=new LinkedBlockingDeque<>();
    //当前要发送的位置信息
    private ArrayList<Map<String, UserGps>>currentSendLocationInfo=new ArrayList<>();

    private OnlineUserManager onlineUserManager;

    public OnlineUser(OnlineUserData userData, Session session ,OnlineUserManager manager){
        this.userData = userData;
        this.session = session;
        this.onlineUserManager = manager;
        messageRepository =  this.onlineUserManager.getService().getMessageRepository();
        groupUserRepository = this.onlineUserManager.getService().getGroupUserRepository();
        notificationRepository = this.onlineUserManager.getService().getNotificationRepository();
        //获取群存储库
        groupRepository=this.onlineUserManager.getService().getGroupRepository();

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
            if(messages.size() == 0)continue;
            groupsMessage.put(groupUuid,messages);

        }

        //发送组消息
        sendGroupMessage(groupsMessage);

        List<Notification> notifications = notificationRepository.findUnReceiveNotificationByReceiveUser(userUuid);
        SendNotificationS sendNotificationS = new SendNotificationS();
        sendNotificationS.setOperateId(WebSocketOperateUtil.Send_Notifications);
        if(notifications.size() >0){
            sendNotificationS.setStatus("发送通知成功");
            for(Notification n : notifications){
                User user = n.getSendUserId();
                if(user != null){
                    SendNotification sendNotification = new SendNotification();

                    sendNotification.setUserUuid(n.receiveUserId.getUuid());
                    sendNotification.setSendUuid(n.sendUserId.getUuid());
                    sendNotification.setSendUserName(n.getSendUserId().getName());
                    sendNotification.setNoticeContent(n.content);
                    sendNotification.setNoticeTime(n.getCreateTime().getTime());
                    sendNotification.setGroupName(n.getGroupId().getName());
                    sendNotification.setStatus(n.getStatus());
                    sendNotification.setGroupId(n.getGroupId().getUuid());
                    sendNotification.setNoticeId(n.getId());
                    sendNotification.setGroupId(n.groupId.getUuid());
                    sendNotification.setGroupPortrait(n.getSendUserId().getPortrait());
                    sendNotificationS.addNotification(sendNotification);
                }
            }
        }else {
            sendNotificationS.setStatus("没任何通知");
        }
        //发送通知
        WebSocket webSocket = new WebSocket(WebSocketOperateUtil.Send_Notifications, sendNotificationS,null);
        sendMessage(webSocket);
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
        //获取当前要发送的位置信息
        ArrayList<Map<String,UserGps>>gps=getCurrentSendLocationInfo();
        if(gps.size()!=0){
            //分类各群用户的位置信息
            System.out.println("转发位置信息的长度："+gps.size());
            Map<String,UserGps>groupLocation=getSendGroupLocationMap(gps);
            System.out.println(groupLocation.keySet().toArray());
            System.out.println("keySet="+groupLocation.keySet());
            sendGroupLocation(groupLocation);
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
          添加等待发送用户的位置信息
       */
    public boolean addWaitToSendUserLocation(Map<String,UserGps> userGps){
        try {
            waitToSendLocationInfo.put(userGps);
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

    /*
           获取需要发送的位置信息
        */
    public ArrayList<Map<String,UserGps>>getCurrentSendLocationInfo(){
        currentSendLocationInfo.clear();
        waitToSendLocationInfo.drainTo(currentSendLocationInfo);
        return currentSendLocationInfo;
    }

    public void sendGroupMessage(Map<String,List<Message>> groupMessageMap){

        Object[] keys = groupMessageMap.keySet().toArray();
        GroupMessageS groupMessageS = new GroupMessageS();
        //设置发送组消息的操作码为22
        groupMessageS.setOperateId(WebSocketOperateUtil.Message_Push);


        for (Object key : keys) {
            String groupIds = (String) key;
            int groupId = Integer.valueOf(groupIds);
            List<Message> groupMessage = groupMessageMap.get(groupIds);
            sendGroupMessage(groupMessageS, groupId, groupMessage);
        }

        WebSocket webSocket = new WebSocket(23,groupMessageS,null);

        sendMessage(webSocket);
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

    public void sendMessage(WebSocket webSocket){

        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        转发位置信息
     */
    public void sendGroupLocation(Map<String,UserGps>groupLocation){
        Object []keys=groupLocation.keySet().toArray();
        System.out.println("转发的群号："+groupLocation.keySet());
        SendGroupLocationS sendGroupLocationS=new SendGroupLocationS();
        sendGroupLocationS.setOperateId(101);
        sendGroupLocationS.setStatus("success");
        sendGroupLocationS.setDes("转发成功");
        for(Object key:keys){
            String groupId=(String)key;
            int id=Integer.valueOf(groupId);
            UserGps gps=groupLocation.get(groupId);
            sendGroupGps(sendGroupLocationS,id,gps);
        }
        WebSocket webSocket=new WebSocket(101,sendGroupLocationS,null);
        sendLocation(webSocket);
    }

    /*
        发送组位置信息
     */
    public void sendGroupGps(SendGroupLocationS groupLocationS,int groupId,UserGps gps){
        SendGroupLocationData sendGroupLocationData=new SendGroupLocationData();
        sendGroupLocationData.setLatitude(gps.getLatitude());
        sendGroupLocationData.setGroupId(groupId);
        sendGroupLocationData.setLongitude(gps.getLonggitude());
        sendGroupLocationData.setUserName(gps.getUser().getName());
        sendGroupLocationData.setUserId(gps.getUser().getUuid());
        groupLocationS.addSendData(sendGroupLocationData);
    }

    /*
        发送位置信息
     */
    public void sendLocation(WebSocket webSocket){
        try {
            if(session.isOpen()){
                OnlineUser onlineUser=onlineUserManager.getOnlineUserBySessionId(session.getId());
                System.out.println("当前用户是否在线："+onlineUser.getSession().isOpen());
                System.out.println("用户是谁："+onlineUser.getData().getUuid());
                session.getBasicRemote().sendObject(webSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    /*
        分类发给群组的消息集合
        key:GroupId
        value:群组消息集合
     */
    public Map<String,List<Message>> getSendGroupMessageMap(List<Message> messages){
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
    /*
            分类发给群组的位置信息集合
         */
    public Map<String ,UserGps>getSendGroupLocationMap(ArrayList<Map<String,UserGps>> gps){
        Map <String,UserGps> gpsMap=new HashMap<>();
        for(Map <String,UserGps> key:gps) {
            Object[] b = key.keySet().toArray();
            System.out.println("key.keySet()=" + key.keySet());
            for (Object obj : b) {
                if (!gpsMap.containsKey(obj)) {
                    gpsMap.put((String) obj, key.get(obj));
                    continue;
                }
            }
        }
        return gpsMap;
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
}

