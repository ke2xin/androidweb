package com.group.zhtx.service;



import com.group.zhtx.message.websocket.client.UserCreateGroupC;
import com.group.zhtx.message.websocket.client.UserGetGroupDataC;
import com.group.zhtx.message.websocket.client.UserLoginC;
import com.group.zhtx.message.controller.register.RegisterC;
import com.group.zhtx.message.websocket.client.UserSaveGroupDataC;
import com.group.zhtx.message.websocket.service.createGroupMessage.UserCreateGroupS;
import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataS;
import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataMember;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginData;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginDataGroup;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginDataSingal;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginS;
import com.group.zhtx.message.websocket.service.saveGroupData.UserSaveGroupDataS;
import com.group.zhtx.model.Group;
import com.group.zhtx.model.GroupUser;
import com.group.zhtx.model.Message;
import com.group.zhtx.model.User;
import com.group.zhtx.repository.*;
import com.group.zhtx.util.common.WebSocketOperateUtil;
import com.group.zhtx.util.group.GroupUtil;
import com.group.zhtx.webSocket.IWebSocketListener;
import com.group.zhtx.webSocket.WebSocket;
import com.group.zhtx.webSocket.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    操作的实现
 */
@Service
public class RepositoryService implements IRepositoryService,IWebSocketListener {

    private static Logger logger = LoggerFactory.getLogger(RepositoryService.class);

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("groupRepository")
    private GroupRepository groupRepository;

    @Autowired
    @Qualifier("groupUserRepository")
    private GroupUserRepository groupUserRepository;

    @Autowired
    @Qualifier("messageRepository")
    private MessageRepository messageRepository;

    @Autowired
    @Qualifier("notificationRepository")
    private NotificationRepository notificationRepository;

    @Autowired
    @Qualifier("userGpsRepository")
    private UserGpsRepository userGpsRepository;



    @PostConstruct
    public void initMethod() throws Exception {
        WebSocketManager.addWebSocketListener(this);
    }

    @Override
    public Map<Integer, String> getWebSocketService() throws Exception {
        Map<Integer,String> map = new HashMap<>();
        map.put(WebSocketOperateUtil.User_Login_C,"userLogin");
        map.put(WebSocketOperateUtil.User_CreateGroup_C,"createGroup");
        return map;
    }


    /*
        保存用户，用于注册
     */
    public void saveUser(RegisterC registerC){
        User user = new User();
        user.setUuid(registerC.getUuid());
        user.setName(registerC.getUuid());
        user.setPassword(registerC.getPassword());
        user.setPhone(registerC.getUserPhone());
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userRepository.save(user);
    }

    /*
        校验用户是否被注册
        params：uuid 用户账号
     */
    public boolean validUserRegister(String uuid){
        User user = userRepository.findById(uuid).orElse(null);
        if(user == null){
            return false;
        }
        return true;
    }

    /*
        用户登陆
     */
    public void userLogin(WebSocket webSocket){
        UserLoginC registerC = (UserLoginC) webSocket.getIMessage();
        Session session = webSocket.getSession();
        String uuid = registerC.getUuid();
        String password = registerC.getPassword();
        User user = userRepository.findById(uuid).orElse(null);

        if(user == null){
            try {
                session.getBasicRemote().sendText("" +
                        "{\n" +
                        "operateId : 1,\n" +
                        "status : fail,\n" +
                        "information : “不存在这个用户或者用户名错误”,\n" +
                        "data:[]\n" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //销毁消息包
            webSocket.clear();
            return;
        }

        if(!password.equals(user.getPassword())){
            try {
                session.getBasicRemote().sendText("" +
                        "{\n" +
                        "operateId : 1,\n" +
                        "status : fail,\n" +
                        "information : “用户密码错误”,\n" +
                        "data:[]\n" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //销毁消息包
            webSocket.clear();
            return;
        }


        UserLoginS userLoginS = new UserLoginS();
        UserLoginData data = new UserLoginData();
        UserLoginDataSingal singal = new UserLoginDataSingal();

        singal.setUserName(user.getName());
        singal.setUserPortrait(user.getPortrait());
        singal.setUserSign(user.getSign());

        data.setSingal(singal);
        List<Group> groups = groupRepository.getGroupByUuid(uuid);

        for(int i = 0; i <groups.size(); i++){
            Group group = groups.get(i);
            String groupUuid = group.getUuid();

            //查找用户当前群组的最新消息
            List<Message> messages = messageRepository.getLastestMessageByGroupUuid();
            Message message = messages.get(0);
            //获取用户最后接受消息时间
            Date receiveTime = groupUserRepository.getGroupLastestTime(user.getUuid(),groupUuid);

            //获取用户接受消息时间段之后的消息数
            int messageCount = messageRepository.getCountOfUnReadMessageByGroupUuidAndTime(groupUuid,receiveTime);

            //根据用户的组ID和用户的id查找用户在当前的角色
            int groupRole =groupUserRepository.getGroupUserRole(user.getUuid(),groupUuid);

            //填写用户组信息
            UserLoginDataGroup loginGroup = new UserLoginDataGroup();
            //设置群名称
            loginGroup.setGroupName(group.getName());
            //设置群号
            loginGroup.setGroupNumber(group.getUuid());

            //设置用户头像
            loginGroup.setGroupPortrait(group.getPortarit());
            loginGroup.setLastestGroupUser(userRepository.getUserName(message.getUser().getUuid()));
            loginGroup.setLastGroupSendTime(message.getSendTime());
            loginGroup.setLastestGroupMessage(message.getContent());
            loginGroup.setGroupMessageCount(messageCount);
            loginGroup.setGroupRole(groupRole);

            data.addDataGroup(loginGroup);
        }

        int operateId = webSocket.getOperateId();

        userLoginS.setOperateId(operateId);
        userLoginS.setStatus("成功");
        userLoginS.setData(data);

        webSocket = new WebSocket(operateId, userLoginS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
            webSocket.clear();
        }

    }

    /*
        用户创建群
     */
    public void createGroup(WebSocket webSocket){
        UserCreateGroupC userCreateGroupC = (UserCreateGroupC) webSocket.getIMessage();
        Session session = webSocket.getSession();
        int operateId = webSocket.getOperateId();
        UserCreateGroupS userCreateGroupS = new UserCreateGroupS();
        userCreateGroupS.setOperateId(operateId);


        //如果申请创建群的用户不存在，直接返回
        User user = userRepository.findById(userCreateGroupC.getUuid()).orElse(null);
        if(user == null){

            userCreateGroupS.setStatus("fail");
            userCreateGroupS.setInformation("创建群的用户不存在");
            webSocket = new WebSocket(operateId, userCreateGroupS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (Exception e) {
                e.printStackTrace();
                webSocket.clear();
            }
            return;
        }

        //循环获取一个没有的群号
        String groupNumber = GroupUtil.getRandomGroupNumber() + "";
        while (groupRepository.existsById(groupNumber)){
            groupNumber = GroupUtil.getRandomGroupNumber() + "";
        }

        Group group = new Group();
        group.setUuid(groupNumber);
        group.setName(userCreateGroupC.getGroupName());
        group.setAnoun(userCreateGroupC.getGroupDec());
        group.setCreater(user);
        group.setCreateTime(new Date());
        group.setStatus((short) 1);
        group.setHobby(userCreateGroupC.getGroupHobby());

        //将用户创建的群加入群组表这中
        groupRepository.save(group);

        GroupUser groupUser = new GroupUser();
        groupUser.setGroup(group);
        groupUser.setJoinTime(new Date());
        groupUser.setReceiveTime(new Date());
        groupUser.setRole((short) 1);
        groupUser.setStatus((short) 1);
        groupUser.setUser(user);

        //创建组管理员用户添加进组成员表中
        groupUserRepository.save(groupUser);


        //返回数据
        userCreateGroupS.setStatus("success");
        userCreateGroupS.setInformation("创建群成功");
        webSocket = new WebSocket(operateId, userCreateGroupS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (Exception e) {
            e.printStackTrace();
            webSocket.clear();
        }
    }

    /*
        获取群资料
     */
    public void getGroupData(WebSocket webSocket){
        UserGetGroupDataC userGetGroupDataC = (UserGetGroupDataC) webSocket.getIMessage();
        Session session = webSocket.getSession();
        int operateId = webSocket.getOperateId();
        //获取用户传入的群号
        String groupUuid = userGetGroupDataC.getGroupId();

        Group group = groupRepository.findById(groupUuid).orElse(null);

        //如果根据用户传入的群号,没有这个群组的就直接返回
        if(group == null){

            try {
                session.getBasicRemote().sendText("{" +
                        "toperateId : 5," +
                        "status : fail," +
                        "data : []" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                //销毁消息包
                webSocket.clear();
                return;
            }

        }

        UserGetGroupDataS data = new UserGetGroupDataS();
        data.setOperateId(operateId);
        data.setGroupName(group.getName());
        data.setGroupNumber(group.getUuid());
        data.setGroupPortrait(group.getPortarit());

        List<User> users = userRepository.getUserByGroupUuid(groupUuid);

        for (User u : users){
            UserGetGroupDataMember member = new UserGetGroupDataMember();
            member.setGroupUserName(u.getName());
            member.setGroupUserPortrait(u.getPortrait());
            member.setGroupUserUuid(u.getUuid());
            data.addMember(member);
        }


        try {
            session.getBasicRemote().sendObject(webSocket);
            WebSocket webSocket1 = new WebSocket(operateId,data,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*
        保存群资料
     */
    public void saveGroupData(WebSocket webSocket){
        UserSaveGroupDataC userSaveGroupDataC = (UserSaveGroupDataC) webSocket.getIMessage();
        int operateId = webSocket.getOperateId();
        Session session = webSocket.getSession();

        Group group = groupRepository.findById(userSaveGroupDataC.getGroupId()).orElse(null);

        if(group == null){
            return;
        }

        //获取发过来的数据，动态修改某部分
        String groupName = userSaveGroupDataC.getGroupName();
        String groupDec = userSaveGroupDataC.getGroupDec();
        String groupHobby = userSaveGroupDataC.getGroupHobby();

        if(groupName != null){
            group.setName(groupName);
        }else if (groupDec != null){
            group.setAnoun(groupDec);
        }else if (groupHobby != null){
            group.setHobby(groupHobby);
        }
        //保存修改的群资料，并刷新缓存
        groupRepository.saveAndFlush(group);

        UserSaveGroupDataS userSaveGroupDataS = new UserSaveGroupDataS();

        userSaveGroupDataS.setOperateId(operateId);
        userSaveGroupDataS.setStatus("success");
        userSaveGroupDataS.setInformation("保存修改群资料成功");


        try {
            webSocket = new WebSocket(operateId,userSaveGroupDataS,null);
            session.getBasicRemote().sendObject(webSocket);
        } catch (Exception e) {
            e.printStackTrace();
            //销毁消息包
            webSocket.clear();
        }
    }
}
