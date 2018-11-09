package com.group.zhtx.service;




import com.group.zhtx.message.websocket.client.*;
import com.group.zhtx.message.controller.register.RegisterC;
import com.group.zhtx.message.controller.register.PasswordC;
import com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup.AcceptAndRefuseInfo;
import com.group.zhtx.message.websocket.service.AcceptAndRefuseEnterGroup.AcceptAndRefuseDataS;
import com.group.zhtx.message.websocket.service.createGroupMessage.UserCreateGroup;
import com.group.zhtx.message.websocket.service.createGroupMessage.UserCreateGroupS;
import com.group.zhtx.message.websocket.service.deleteGroupData.DeleteDataS;
import com.group.zhtx.message.websocket.service.deleteGroupData.DeleteInfo;
import com.group.zhtx.message.websocket.service.dissolutionData.DissolutionDataS;
import com.group.zhtx.message.websocket.service.dissolutionData.DissolutionInfo;
import com.group.zhtx.message.websocket.service.dissolutionData.DissolutionNotificationInfo;
import com.group.zhtx.message.websocket.service.dissolutionData.DissolutionNotificationS;
import com.group.zhtx.message.websocket.service.enterGroup.EnterDataInfo;
import com.group.zhtx.message.websocket.service.enterGroup.EnterGroupDataS;
import com.group.zhtx.message.websocket.service.exitData.ExitDataS;
import com.group.zhtx.message.websocket.service.exitData.ExitInfo;
import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataS;
import com.group.zhtx.message.websocket.service.getGroupData.UserGetGroupDataMember;
import com.group.zhtx.message.websocket.service.groupNumberInfo.GroupNumberDataS;
import com.group.zhtx.message.websocket.service.groupNumberInfo.GroupNumberInfo;
import com.group.zhtx.message.websocket.service.locationData.UserLocationGroup;
import com.group.zhtx.message.websocket.service.locationData.UserLocationS;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginData;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginDataGroup;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginDataSingal;
import com.group.zhtx.message.websocket.service.loginMessage.UserLoginS;
import com.group.zhtx.message.websocket.service.myData.MyDataInfo;
import com.group.zhtx.message.websocket.service.myData.MyDataS;
import com.group.zhtx.message.websocket.service.quitData.QuitDataS;
import com.group.zhtx.message.websocket.service.savaPersonalData.SavePersonalDataS;
import com.group.zhtx.message.websocket.service.savaPersonalData.SavePersonalInfo;
import com.group.zhtx.message.websocket.service.saveGroupData.UserSaveGroupDataS;
import com.group.zhtx.message.websocket.service.sendGroupMessage.SendGroupMessageS;
import com.group.zhtx.message.websocket.service.userSendTimeStamp.UserSendTimeStampS;
import com.group.zhtx.model.Group;
import com.group.zhtx.model.GroupUser;
import com.group.zhtx.model.Message;
import com.group.zhtx.model.User;
import com.group.zhtx.onlineUser.OnlineUser;
import com.group.zhtx.onlineUser.OnlineUserData;
import com.group.zhtx.onlineUser.OnlineUserManager;
import com.group.zhtx.message.websocket.service.searchData.SearchDataInfo;
import com.group.zhtx.message.websocket.service.searchData.SearchDataS;
import com.group.zhtx.message.websocket.service.telephoneBook.RelativeBookS;
import com.group.zhtx.message.websocket.service.telephoneBook.RelativeInfo;
import com.group.zhtx.model.*;
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
import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

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

    @Resource
    private OnlineUserManager onlineUserManager;

    @PostConstruct
    public void initMethod() throws Exception {
        WebSocketManager.addWebSocketListener(this);
    }

    @Override
    public Map<Integer, String> getWebSocketService() throws Exception {
        Map<Integer,String> map = new HashMap<>();
        map.put(WebSocketOperateUtil.User_Login_C,"userLogin");
        map.put(WebSocketOperateUtil.User_CreateGroup_C,"createGroup");
        map.put(WebSocketOperateUtil.User_Enter_Group_C,"enterGroup");
        map.put(WebSocketOperateUtil.User_Get_Group_Data_C,"getGroupData");
        map.put(WebSocketOperateUtil.User_Quit_Group_C,"quitGroup");
        map.put(WebSocketOperateUtil.User_Group_Number_Location_C,"searchGroupNumberLocation");
        map.put(WebSocketOperateUtil.User_Phone_Relative_C,"relativeNumberByPhone");
        map.put(WebSocketOperateUtil.User_Application_Enter_Group_C,"applicaEnterGroup");
        map.put(WebSocketOperateUtil.User_Accept_Enter_Group_C,"acceptUserEnterGroup");
        map.put(WebSocketOperateUtil.User_Refuse_Enter_Group_C,"refuseEnterGroup");
        map.put(WebSocketOperateUtil.User_Delete_Group_Number_C,"deleteGroupNumber");
        map.put(WebSocketOperateUtil.User_Search_Group_C,"searchGroup");
        map.put(WebSocketOperateUtil.User_Search_Group_Number_Info_C,"searchGroupNumberInfo");
        map.put(WebSocketOperateUtil.User_For_Me_C,"SearchForMe");
        map.put(WebSocketOperateUtil.User_Data_Info,"searchGroupNumberInfo");
        map.put(WebSocketOperateUtil.User_Save_Personal_Info,"savePersonalInfo");
        map.put(WebSocketOperateUtil.User_Exit,"UserExit");
        map.put(WebSocketOperateUtil.User_Dissolution_Group,"dissolutionGroup");
        map.put(WebSocketOperateUtil.User_Send_GroupMessage_C,"getSendGroupMessage");
        map.put(WebSocketOperateUtil.User_Send_TimeStamp,"getReceiveTimeStamp");
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
           检验用户是否在使用该手机号
    */
    public boolean validUserPhone(String uuid,String phone){
        List<User> user=userRepository.findByUuidAndPhone(uuid,phone);
        System.out.println(user);
        if(user==null){
            return false;
        }
        return true;
    }

    /*
        检测该电话是否被注册
     */
    public boolean isPhoneRegistered(String phone){
        System.out.println("phone="+phone);
        User user=userRepository.findByPhone(phone);
        System.out.println("user="+user);
        if(user==null)
            return false;
        else
             return true;
    }


    /*
       根据电话号码查找用户
     */
    public User getUserByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    /*
        发送消息
     */
    private void sendMessageWithWebSocket(Session session,WebSocket webSocket){
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (Exception e) {
            e.printStackTrace();
            webSocket.clear();
        }
        //销毁消息包
        webSocket.clear();
    }

    private void sendMessageWithText(Session session,String text){
        try {
            session.getBasicRemote().sendText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        根据绑定的电话更新用户的密码
        @Transactional注解用于声明方法的事物特性
     */
    @Transactional
    public void updateNewPassword(User user, PasswordC passwordC){
        user.setPassword(passwordC.getNew_password());
    }

    /*
        检测用户是否已经登录
        param: sessionId 必须
        param: userId 不必须
     */
    private boolean checkUserIsOnline(String sessiondId,String userUuid){
        OnlineUser onlineUser = onlineUserManager.getOnlineUserBySessionId(sessiondId);

        if (onlineUser == null){
            if(userUuid == null){
                return false;
            }
            onlineUser = onlineUserManager.getOnlineUserByUuid(userUuid);
            if(onlineUser == null){
                return false;
            }
            return true;
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

        UserLoginS userLoginS = new UserLoginS();

        if(user == null){
            try {
                userLoginS.setOperateId(registerC.getOperateCode());
                userLoginS.setStatus("fail");
                userLoginS.setInformation("不存在这个用户或者用户名错误");
                webSocket = new WebSocket(registerC.getOperateCode(),userLoginS,null);
                session.getBasicRemote().sendObject(webSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //销毁消息包
            webSocket.clear();
            return;
        }

        if(!password.equals(user.getPassword())){
            try {
                userLoginS.setOperateId(registerC.getOperateCode());
                userLoginS.setStatus("fail");
                userLoginS.setInformation("用户密码错误");
                webSocket = new WebSocket(registerC.getOperateCode(),userLoginS,null);
                session.getBasicRemote().sendObject(webSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //销毁消息包
            webSocket.clear();
            return;
        }



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
            List<Message> messages = messageRepository.getLastestMessageByGroupUuid(groupUuid);


            //获取用户最后接受消息时间
            Date receiveTime = groupUserRepository.getGroupLastestTime(user.getUuid(),groupUuid);

            //获取用户接受消息时间段之后的消息数
            int messageCount = messageRepository.getCountOfUnReadMessageByGroupUuidAndTime(groupUuid,receiveTime);

            //根据用户的组ID和用户的id查找用户在当前的角色
            int groupRole =groupUserRepository.getGroupUserRole(user.getUuid(),groupUuid);

            //填写用户组信息
            UserLoginDataGroup loginGroup = new UserLoginDataGroup();

            if(messages.size() >0){
                Message message = messages.get(0);
                loginGroup.setLastestGroupUser(userRepository.getUserName(message.getUser().getUuid()));
                loginGroup.setLastGroupSendTime(message.getSendTime());
                loginGroup.setLastestGroupMessage(message.getContent());
            }else {
                loginGroup.setLastestGroupUser("");
                loginGroup.setLastGroupSendTime(new Date());
                loginGroup.setLastestGroupMessage("");
            }
            //设置群名称
            loginGroup.setGroupName(group.getName());
            //设置群号
            loginGroup.setGroupNumber(group.getUuid());

            //设置用户头像
            loginGroup.setGroupPortrait(group.getPortarit());

            loginGroup.setGroupMessageCount(messageCount);
            loginGroup.setGroupRole(groupRole);
            data.addDataGroup(loginGroup);
        }

        int operateId = webSocket.getOperateId();
        userLoginS.setOperateId(operateId);
        userLoginS.setStatus("success");
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

        //创建在线用户数据
        OnlineUserData onlineUserData = new OnlineUserData(user.getUuid(),user.getPhone(),user.getName(),user.getPortrait());
        //创建在线用户
        OnlineUser onlineUser = new OnlineUser(onlineUserData,session,groupUserRepository,messageRepository,onlineUserManager);
        //设置用户在线状态
        onlineUser.setOnline(true);
        //之后操作的唯一管理识别类
        onlineUserManager.addOnlineUser(onlineUser);
    }

    /*
        用户创建群
     */
    public void createGroup(WebSocket webSocket){
        Session session = webSocket.getSession();
        UserCreateGroupC userCreateGroupC = (UserCreateGroupC) webSocket.getIMessage();
        int operateId = webSocket.getOperateId();
        UserCreateGroupS userCreateGroupS = new UserCreateGroupS();
        userCreateGroupS.setOperateId(operateId);
        String userUuid = userCreateGroupC.getUuid();
        System.out.println("创建群");

        //检测用户是否登录
        if(!checkUserIsOnline(session.getId(),userUuid))return;

        //如果申请创建群的用户不存在，直接返回
        User user = userRepository.findById(userCreateGroupC.getUuid()).orElse(null);
        if(user == null){
            userCreateGroupS.setStatus("fail");
            userCreateGroupS.setInformation("创建群的用户不存在");
            webSocket = new WebSocket(operateId, userCreateGroupS,null);
            sendMessageWithWebSocket(session,webSocket);
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
        //设置群主角色
        groupUser.setRole((short) 0);
        groupUser.setStatus((short) 1);
        groupUser.setUser(user);

        //创建组管理员用户添加进组成员表中
        groupUserRepository.save(groupUser);



        //返回数据和所有群
        userCreateGroupS.setStatus("success");
        userCreateGroupS.setInformation("创建群成功");
        List<Group> groupLists=groupUserRepository.getGroupsByUserUuid(userCreateGroupC.getUuid());
        System.out.println("你拥有的群度："+groupLists.size());
        for(int i=0;i<groupLists.size();i++){
            Group group1=groupLists.get(i);
            if(groupNumber.equals(group1.getUuid())){//用户刚刚创建的群
                    UserCreateGroup createGroup = new UserCreateGroup();
                    createGroup.setGroupId(group.getUuid());
                    createGroup.setGroupName(group.getName());
                    createGroup.setGroupPortrait(group.getPortarit());
                    createGroup.setLastestGroupUser("null");
                    createGroup.setLastGroupNumberName("null");
                    createGroup.setLastGroupSendTime(-1);
                    createGroup.setLastestGroupMessage("");
                    createGroup.setGroupMessageCount(0);
                    createGroup.setGroupRole(0);
                    userCreateGroupS.addUserCreateGroup(createGroup);
            }else{//用户已拥有的群
                UserCreateGroup createGroup = new UserCreateGroup();
                createGroup.setGroupName(group1.getName());
                createGroup.setGroupId(group1.getUuid());
                createGroup.setGroupPortrait(group1.getPortarit());
                //获取群最新消息
                List<Message> messageLists=messageRepository.getLastestMessageByGroupUuid(group1.getUuid());
                if(messageLists.size()==0){
                    createGroup.setLastestGroupUser("null");
                    createGroup.setLastGroupNumberName("");
                    createGroup.setLastGroupSendTime(-1);
                    createGroup.setLastestGroupMessage("");
                    createGroup.setGroupMessageCount(0);
                    createGroup.setGroupRole(-1);
                }else {
                    Message message = messageLists.get(0);
                    User lastUser = userRepository.findByUuid(message.getUser().getUuid());
                    createGroup.setLastestGroupUser(lastUser.getUuid());
                    createGroup.setLastGroupNumberName(lastUser.getName());
                    createGroup.setLastGroupSendTime(message.getSendTime().getTime());
                    createGroup.setLastestGroupMessage(message.getContent());
                    //获取用户最后接受消息时间
                    Date receiveTime = groupUserRepository.getGroupLastestTime(lastUser.getUuid(), group1.getUuid());
                    createGroup.setGroupMessageCount(messageRepository.getCountOfUnReadMessageByGroupUuidAndTime(group1.getUuid(), receiveTime));
                    createGroup.setGroupRole(groupUser.getRole());
                }
                userCreateGroupS.addUserCreateGroup(createGroup);
            }
        }
        webSocket = new WebSocket(operateId, userCreateGroupS,null);
        sendMessageWithWebSocket(session,webSocket);
    }

    /*
        进入群聊
     */
    public void enterGroup(WebSocket webSocket){
        System.out.println("进入群聊");
        UserEnterGroupC enterGroupC=(UserEnterGroupC) webSocket.getIMessage();
        int operateId=enterGroupC.getOperateId();
        Session session=webSocket.getSession();
        List<Message>messages=messageRepository.getChatMessageByGroupId(enterGroupC.getGroup_uuid(),enterGroupC.getGroup_uuid());
        EnterGroupDataS groupDataS=new EnterGroupDataS();
        List<EnterDataInfo>data=new ArrayList<>();
        if(messages.size()==0){
            groupDataS.setOperateId(operateId);
            groupDataS.setStatus("该群没有最近的聊天的信息");
            groupDataS.setData(data);
            webSocket=new WebSocket(operateId,groupDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        for(int i=0;i<messages.size();i++){
            Message message=messages.get(i);
            User user=userRepository.findByUuid(message.getUser().getUuid());
            if(user==null){//如果发现不存在的用户时，直接返回
                groupDataS.setOperateId(operateId);
                groupDataS.setStatus("查找聊天记录失败");
                groupDataS.setData(data);
                webSocket=new WebSocket(operateId,groupDataS,null);
                try {
                    session.getBasicRemote().sendObject(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
                webSocket.clear();
                return;
            }
            EnterDataInfo info=new EnterDataInfo();
            System.out.println("user="+user);
            info.setUserPortrait(user.getPortrait());
            info.setUserMessage(message.getContent());
            info.setUsername(user.getName());
            info.setUserSendTime(message.getSendTime().toString());
            data.add(info);
        }
        groupDataS.setOperateId(operateId);
        groupDataS.setStatus("success");
        groupDataS.setData(data);
        webSocket=new WebSocket(operateId,groupDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        webSocket.clear();
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

        //检测用户是否登录
        if(!checkUserIsOnline(session.getId(),null))return;

        Group group = groupRepository.findById(groupUuid).orElse(null);

        //如果根据用户传入的群号,没有这个群组的就直接返回
        if(group == null){

            try {
                session.getBasicRemote().sendText("{" +
                        "operateId : 5," +
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
        data.setGroupAnoun(group.getAnoun());
        List<User> users = userRepository.getUserByGroupUuid(groupUuid);

        for (User u : users){
            UserGetGroupDataMember member = new UserGetGroupDataMember();
            member.setGroupUserName(u.getName());
            member.setGroupUserPortrait(u.getPortrait());
            member.setGroupUserUuid(u.getUuid());
            data.addMember(member);
        }

        webSocket = new WebSocket(operateId,data,null);
        sendMessageWithWebSocket(session,webSocket);

    }

    /*
        退出群聊
     */
    public void quitGroup(WebSocket webSocket){
        System.out.println("退出群聊");
        UserQuitGroupC userQuitGroupC=(UserQuitGroupC) webSocket.getIMessage();
        int operateId=userQuitGroupC.getOperateId();
        Session session=webSocket.getSession();
        GroupUser groupUser=groupUserRepository.getGroupUserByGroupAndUuid(userQuitGroupC.getUser_uuid(),userQuitGroupC.getGroup_id());
        System.out.println("uuid="+userQuitGroupC.getUser_uuid()+"\tgroupId="+userQuitGroupC.getGroup_id());
        System.out.println(groupUser);
        QuitDataS quitDataS=new QuitDataS();
        if(groupUser==null){//不存在该群号时直接返回
            quitDataS.setOperateId(operateId);
            quitDataS.setStatus("fail");
            quitDataS.setInformation("不存在该群号或者不存在该成员");
            webSocket=new WebSocket(operateId,quitDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        if(groupUser.getRole()==1){//判断该成员是否是群成员还是群主，0表示群主，1表示群成员
            groupUserRepository.delete(groupUser);
            quitDataS.setOperateId(operateId);
            quitDataS.setStatus("success");
            quitDataS.setInformation("成功退出群聊");
            webSocket=new WebSocket(operateId,quitDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }else {
            quitDataS.setOperateId(operateId);
            quitDataS.setStatus("fail");
            quitDataS.setInformation("非法操作,你没有该权限");
            webSocket=new WebSocket(operateId,quitDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
    }

    /*
        保存群资料
     */
    public void saveGroupData(WebSocket webSocket){
        UserSaveGroupDataC userSaveGroupDataC = (UserSaveGroupDataC) webSocket.getIMessage();
        int operateId = webSocket.getOperateId();
        Session session = webSocket.getSession();

        //检测用户是否登录
        if(!checkUserIsOnline(session.getId(),null))return;

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

    /*
        群里发送消息
     */
    public void getSendGroupMessage(WebSocket webSocket){
        SendGroupMessageC sendGroupMessageC = (SendGroupMessageC) webSocket.getIMessage();
        int operateId = webSocket.getOperateId();
        Session session = webSocket.getSession();

        //检测用户是否登录
        if(!checkUserIsOnline(session.getId(),null))return;

        OnlineUser onlineUser = onlineUserManager.getOnlineUserByUuid(sendGroupMessageC.getUserUuid());
        if(onlineUser == null){
            onlineUser = onlineUserManager.getOnlineUserBySessionId(session.getId());
            if (onlineUser == null)return;
        }

        String groupId = sendGroupMessageC.getGroupUuid();
        String userId = sendGroupMessageC.getUserUuid();

        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if(group == null || user == null){
            return;
        }

        //获取内容
        String content = sendGroupMessageC.getContent();

        Message message = new Message();
        message.setContent(content);
        message.setGroup(group);
        message.setUser(user);
        message.setSendTime(new Date());
        //设置消息未读
        message.setStatus((short) 0);

        //服务器记录聊天
        messageRepository.save(message);

        //查找所有的群成员
        List<GroupUser> groupUsers = groupUserRepository.findByGroup(group);
        for (GroupUser gu : groupUsers){
            String currentgroupUuid = gu.getUser().getUuid();
            OnlineUser currentOnlineUser = onlineUserManager.getOnlineUserByUuid(currentgroupUuid);
            //如果在线用户没有这个Id，无视
            if(currentOnlineUser == null)continue;
            currentOnlineUser.addWaitToSendMessage(message);
        }

        SendGroupMessageS sendGroupMessageS = new SendGroupMessageS();
        sendGroupMessageS.setContent(content);
        sendGroupMessageS.setInformation("接收成功");
        sendGroupMessageS.setOperateId(operateId);
        sendGroupMessageS.setStatus("success");

        webSocket = new WebSocket(operateId,sendGroupMessageS,null);
        sendMessageWithWebSocket(session,webSocket);
    }

    /*
        更新用户接收时间
     */
    public void getReceiveTimeStamp(WebSocket webSocket){
        UserSendTimeStampC userSendTimeStampC = (UserSendTimeStampC) webSocket.getIMessage();
        String userUuid = userSendTimeStampC.getUserUuid();
        String groupUuid = userSendTimeStampC.getGroupUuid();
        int operateId = webSocket.getOperateId();
        Session session = webSocket.getSession();

        User user = userRepository.findById(userUuid).orElse(null);

        UserSendTimeStampS userSendTimeStampS = new UserSendTimeStampS();
        userSendTimeStampS.setOperateId(webSocket.getOperateId());

        if(user == null){
            userSendTimeStampS.setInformation("用户不存在啊");
            userSendTimeStampS.setStatus("fail");
            webSocket = new WebSocket(operateId, userSendTimeStampS, null);
            sendMessageWithWebSocket(session, webSocket);
        }

        Group group = groupRepository.findById(groupUuid).orElse(null);

        if(group == null){
            userSendTimeStampS.setInformation("该群不存在");
            userSendTimeStampS.setStatus("fail");
            webSocket = new WebSocket(operateId, userSendTimeStampS, null);
            sendMessageWithWebSocket(session, webSocket);
        }

        GroupUser groupUser = groupUserRepository.getGroupUserByGroupAndUser(user, group);

        if(groupUser == null){
            userSendTimeStampS.setInformation("不是该群的成员或者该群不存在");
            userSendTimeStampS.setStatus("fail");
            webSocket = new WebSocket(operateId, userSendTimeStampS, null);
            sendMessageWithWebSocket(session, webSocket);
        }

        Date date = new Date(userSendTimeStampC.getTimeStamp());

        groupUser.setReceiveTime(date);
        groupUserRepository.saveAndFlush(groupUser);

        userSendTimeStampS.setTimeStamp(userSendTimeStampC.getTimeStamp());
        userSendTimeStampS.setInformation("更新时间成功");
        userSendTimeStampS.setGroupId(userSendTimeStampC.getGroupUuid());
        webSocket = new WebSocket(operateId, userSendTimeStampS, null);
        sendMessageWithWebSocket(session,webSocket);
    }

    /*
        查看群成员位置信息
     */
    public void searchGroupNumberLocation(WebSocket webSocket){
        System.out.println("正在查看群成员位置信息");
        UserLocationInfoC userLocationInfoC =(UserLocationInfoC) webSocket.getIMessage();
        System.out.println("群号："+ userLocationInfoC.getGroup_id());
        Session session = webSocket.getSession();
        String group_id= userLocationInfoC.getGroup_id();
        Group group=groupRepository.findById(group_id).orElse(null);
        if(group==null){
            try{
                session.getBasicRemote().sendText("{"+
                        "operateId:"+ userLocationInfoC.getOperateId()+","+
                        "status:\"fail\","+
                        "data:[]"+
                        "}");
            }catch (IOException e){
                e.printStackTrace();
                //清除消息包
                webSocket.clear();
            }
            return;
        }
        List<GroupUser>groupUsers=groupUserRepository.findByGroup(group);
        System.out.println("群规模"+groupUsers.size());
        UserLocationS userLocationS=new UserLocationS();
        userLocationS.setOperateId(userLocationInfoC.getOperateId()+"");
        userLocationS.setStatus("success");
        List<UserLocationGroup> data=new ArrayList<>();
        for(int i=0;i<groupUsers.size();i++){
            GroupUser gu=groupUsers.get(i);
            System.out.println(gu.getUser().getUuid());
            User user=userRepository.findById(gu.getUser().getUuid()).orElse(null);
            if(user!=null){
                UserGps userGps=userGpsRepository.findByUser(user);
                System.out.println(userGps.getLatitude());
                UserLocationGroup userLocationGroupS=new UserLocationGroup();
                userLocationGroupS.setUserName("dddd"+user.getName());
                System.out.println(user.getName()+"||\t"+user.getPortrait());
                userLocationGroupS.setUserPortarit(user.getPortrait());
                userLocationGroupS.setUser_location_longitude(userGps.getLonggitude());
                userLocationGroupS.setUser_location_latitude(userGps.getLatitude());
                userLocationGroupS.setUser_location_corner(userGps.getDirectionAndAngle());
                userLocationGroupS.setUser_location_time(new Date().toString());
                data.add(userLocationGroupS);
            }else{
                return;
            }
        }
        userLocationS.setData(data);
        int operateId=webSocket.getOperateId();
        webSocket=new WebSocket(operateId,userLocationS,null);
        try{
            session.getBasicRemote().sendObject(webSocket);
        }catch (IOException e){
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    /*
        电话联系群成员
     */
    public void relativeNumberByPhone(WebSocket webSocket){
        System.out.println("我是电话联系成员");
        UserRelativNumberC userRelativNumber=(UserRelativNumberC) webSocket.getIMessage();
        Session session=webSocket.getSession();
        System.out.println("我是电话联系成员"+session);
        Group group=groupRepository.findById(userRelativNumber.getGroup_id()).orElse(null);
        int operateId=userRelativNumber.getOperateId();
        RelativeBookS relativeBookS=new RelativeBookS();
        if(group==null){
            relativeBookS.setOperateId(userRelativNumber.getOperateId()+"");
            relativeBookS.setStatus("fail");
            relativeBookS.setData(new ArrayList<>());
            webSocket=new WebSocket(operateId,relativeBookS,null);
            try {
                System.out.println("我是电话联系成员"+webSocket.getSession());
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
                webSocket.clear();
            } catch (EncodeException e) {
                e.printStackTrace();
                webSocket.clear();
            }
            return;
        }
        List<GroupUser>groupUsers=groupUserRepository.findByGroup(group);
        System.out.println(groupUsers.get(0).getUser().getUuid());
        relativeBookS.setOperateId(userRelativNumber.getOperateId()+"");
        relativeBookS.setStatus("success");
        List<RelativeInfo>relativeInfoSs=new ArrayList<>();
        for(int i=0;i<groupUsers.size();i++){
            GroupUser groupUser=groupUsers.get(i);
            User user=userRepository.findByUuid(groupUser.getUser().getUuid());
            RelativeInfo relativeInfoS=new RelativeInfo();
            relativeInfoS.setUserName(user.getName());
            relativeInfoS.setUserPortarit(user.getPortrait());
            relativeInfoS.setUserPhone(user.getPhone());
            relativeInfoSs.add(relativeInfoS);
        }
        relativeBookS.setData(relativeInfoSs);
        webSocket=new WebSocket(operateId,relativeBookS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
            webSocket.clear();
        } catch (EncodeException e) {
            e.printStackTrace();
            //销毁消息包
            webSocket.clear();
        }
    }

    /*
    申请加入群聊
     */
    public void applicaEnterGroup(WebSocket webSocket){
        System.out.println("申请加入群");
        Session session=webSocket.getSession();
        int operateId=webSocket.getOperateId();
        UserApplicationEnterGroupC applicationEnterGroupC=(UserApplicationEnterGroupC) webSocket.getIMessage();
        System.out.println("申请加入的群号："+applicationEnterGroupC.getGroup_id()+"\t申请方："+applicationEnterGroupC.getUuid());
        Group group=groupRepository.findByUuid(applicationEnterGroupC.getGroup_id());
        //没有该群号直接返回
        if(group==null){
            try {
                //结果码有三种，-1代表请求失败，0代表发送成功，正在审核，1代表拒绝用户加入群聊，2代表同意用户加入群聊
                session.getBasicRemote().sendText("{"+
                        "operateId:"+operateId+","+
                        "status:\"fail\","+
                        "resultCode:-1,"+
                        "data:[],"+
                        "information:\"不存在该群号\""+
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                webSocket.clear();
            }
            return;
        }
        System.out.println("groups="+group.getCreater().getUuid());
        User receiveUser=group.getCreater();
        User sendUser=userRepository.findByUuid(applicationEnterGroupC.getUuid());
        if(receiveUser!=null&&sendUser!=null){
            Notification notification=new Notification();
            notification.setContent("申请加入群聊");
            notification.setCreateTime(new Date());
            notification.setGroupId(group);
            notification.setResult(0);
            notification.setStatus(0);
            notification.setReceiveUserId(receiveUser);
            notification.setSendUserId(sendUser);
            notificationRepository.save(notification);
            try {
                session.getBasicRemote().sendText("{"+
                        "operateId:"+operateId+","+
                        "status:\"success\","+
                        "resultCode:"+"0,"+
                        "data:"+"[],"+
                        "information:\"发送成功，等待管理员审核\""+
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                webSocket.clear();
            }
        }else{
            try {
                session.getBasicRemote().sendText("{"+
                        "operateId:"+operateId+","+
                        "status:\"fail\","+
                        "resultCode:-1,"+
                        "data:[],"+
                        "information:\"申请加入失败\""+
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                webSocket.clear();
            }
            return;
        }
    }
    /*
        接受用户加入群聊
     */
    public void acceptUserEnterGroup(WebSocket webSocket){


        UserAcceptAndRefuseEnterGroupC userAcceptEnterGroup=(UserAcceptAndRefuseEnterGroupC) webSocket.getIMessage();
        System.out.println("接受加入群聊的群号："+userAcceptEnterGroup.getGroup_uuid());
        System.out.println("接受加入群聊:"+userAcceptEnterGroup.getResult());
        AcceptAndRefuseDataS data=new AcceptAndRefuseDataS();
        AcceptAndRefuseInfo info=new AcceptAndRefuseInfo();
        Session session=webSocket.getSession();
        int operateId=webSocket.getOperateId();



        if(userAcceptEnterGroup.getResult().equals("accept")){//判断一下是否同意加入群聊
            Group group=groupRepository.findByUuid(userAcceptEnterGroup.getGroup_uuid());//判断是否存在这样的一个群
            GroupUser groupUser=groupUserRepository.findByUserAndGroup(userRepository.findByUuid(userAcceptEnterGroup.getRequest_user_uuid()),group);//判断一下是否已是成员
            System.out.println("groupUser="+groupUser);
            if(group!=null&&groupUser==null){//如果存在这样的一个群，且没有这个人，群主就把这个人加到这个群里面
                User user=userRepository.findByUuid(userAcceptEnterGroup.getRequest_user_uuid());
                GroupUser newGroupUser=new GroupUser();
                newGroupUser.setGroup(group);
                newGroupUser.setUser(user);
                newGroupUser.setJoinTime(new Date());
                newGroupUser.setReceiveTime(new Date());
                newGroupUser.setRole((short) 1);
                newGroupUser.setStatus((short) 1);
                groupUserRepository.save(newGroupUser);

                //成功加入群聊之后，改变通知表的状态
                User receiver=userRepository.findByUuid(userAcceptEnterGroup.getSend_user_uuid());
                User sender=userRepository.findByUuid(userAcceptEnterGroup.getRequest_user_uuid());
                Notification notification=notificationRepository.findByReceiveUserIdAndSendUserId(receiver,sender);
                System.out.println("receiver="+receiver.getUuid()+"\tsender="+sender.getUuid()+"\tnotification="+notification);
                notification.setStatus(1);//1表示已读
                notification.setResult(1);//1表示同意
                notification.setContent("同意加入群聊");
                notificationRepository.saveAndFlush(notification);



                data.setOperateId(operateId);
                data.setInformation("成功加入群聊");
                info.setGroup_uuid(userAcceptEnterGroup.getGroup_uuid());
                info.setRequest_user_uuid(userAcceptEnterGroup.getRequest_user_uuid());
                data.setData(info);
                webSocket=new WebSocket(operateId,data,null);
                try {
                    session.getBasicRemote().sendObject(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
                webSocket.clear();
            }else{
                data.setOperateId(operateId);
                data.setData(info);
                data.setInformation("你已是该成员");
                try {
                    webSocket=new WebSocket(operateId,data,null);
                    session.getBasicRemote().sendObject(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
                webSocket.clear();
                return;
            }
        }
    }

    /*
        拒绝用户加入群聊
     */
    public void refuseEnterGroup(WebSocket webSocket){
        System.out.println("拒绝加入群聊");
        UserAcceptAndRefuseEnterGroupC acceptAndRefuseEnterGroup=(UserAcceptAndRefuseEnterGroupC) webSocket.getIMessage();
        int operateId=acceptAndRefuseEnterGroup.getOperateId();
        Session session=webSocket.getSession();
        String result=acceptAndRefuseEnterGroup.getResult();
        AcceptAndRefuseDataS acceptAndRefuseData=new AcceptAndRefuseDataS();
        AcceptAndRefuseInfo acceptAndRefuseInfo=new AcceptAndRefuseInfo();
        if(result.equals("refuse")){
            //拒绝用户加入群之后，改变通知表的状态
            User receive=userRepository.findByUuid(acceptAndRefuseEnterGroup.getSend_user_uuid());
            User sender=userRepository.findByUuid(acceptAndRefuseEnterGroup.getRequest_user_uuid());
            Notification notification=notificationRepository.findByReceiveUserIdAndSendUserId(receive,sender);
            notification.setStatus(1);//已读
            notification.setResult(2);//拒绝
            notification.setContent("拒绝用户加入群聊");
            notificationRepository.saveAndFlush(notification);
            acceptAndRefuseData.setOperateId(operateId);
            acceptAndRefuseData.setInformation("已成功拒绝用户加入群聊");
            acceptAndRefuseInfo.setGroup_uuid(acceptAndRefuseEnterGroup.getGroup_uuid());
            acceptAndRefuseInfo.setRequest_user_uuid(acceptAndRefuseEnterGroup.getRequest_user_uuid());
            acceptAndRefuseData.setData(acceptAndRefuseInfo);
            webSocket=new WebSocket(operateId,acceptAndRefuseData,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }else {
            acceptAndRefuseData.setOperateId(operateId);
            acceptAndRefuseData.setInformation("未知性错误");
            acceptAndRefuseData.setData(acceptAndRefuseInfo);
            webSocket=new WebSocket(operateId,acceptAndRefuseData,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }
    }
    /*
        管理群成员
     */
    public void deleteGroupNumber(WebSocket webSocket){
        System.out.println("删除群成员");
        UserDeleteGroupNumberC deleteGroupNumber=(UserDeleteGroupNumberC) webSocket.getIMessage();
        int operateId=deleteGroupNumber.getOperateId();
        Session session=webSocket.getSession();
        Group group=groupRepository.findByUuid(deleteGroupNumber.getGroup_id());
        User user=userRepository.findByUuid(deleteGroupNumber.getUuid());
        System.out.println("group="+group+"\tuser="+user);
        System.out.println("group="+deleteGroupNumber.getGroup_id()+"\tuser="+user.getUuid());
        DeleteDataS deleteDataS=new DeleteDataS();
        DeleteInfo deleteInfo=new DeleteInfo();
        if(group!=null&&user!=null){
            GroupUser groupUser=groupUserRepository.findByUserAndGroup(user,group);
            if(groupUser.getRole()==0){//如果是群主，就把这样的一个用户删除
                User deleteUser=userRepository.findByUuid(deleteGroupNumber.getDel_uuid());
                GroupUser deleteGroupUser=groupUserRepository.findByUserAndGroup(deleteUser,group);
                if(deleteGroupUser!=null){
                    groupUserRepository.delete(deleteGroupUser);
                    deleteDataS.setOperateId(operateId);
                    deleteDataS.setInformation("删除成功");
                    deleteDataS.setStatus("success");
                    deleteDataS.setData(deleteInfo);
                    webSocket=new WebSocket(operateId,deleteDataS,null);
                    try {
                        session.getBasicRemote().sendObject(webSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }
                    webSocket.clear();
                }else {
                    deleteDataS.setOperateId(operateId);
                    deleteDataS.setInformation("没有该成员要删除的");
                    deleteDataS.setStatus("fail");
                    deleteDataS.setData(deleteInfo);
                    webSocket=new WebSocket(operateId,deleteDataS,null);
                    try {
                        session.getBasicRemote().sendObject(webSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }
                    webSocket.clear();
                }
            }else{
                deleteDataS.setOperateId(operateId);
                deleteDataS.setInformation("没有权限删除该成员");
                deleteDataS.setStatus("fail");
                deleteDataS.setData(deleteInfo);
                webSocket=new WebSocket(operateId,deleteDataS,null);
                try {
                    session.getBasicRemote().sendObject(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
                webSocket.clear();
            }
        }else{
            deleteDataS.setOperateId(operateId);
            deleteDataS.setInformation("删除失败");
            deleteDataS.setStatus("fail");
            deleteDataS.setData(deleteInfo);
            webSocket=new WebSocket(operateId,deleteDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }
    }
    /*
        搜索群
     */
    public void searchGroup(WebSocket webSocket){
        System.out.println("搜索群");
        UserSearchGroupC userSearchGroupC=(UserSearchGroupC) webSocket.getIMessage();
        int operateId=webSocket.getOperateId();
        Session session=webSocket.getSession();
        List<Group> groups=groupRepository.findByUuidLike("%"+userSearchGroupC.getGroup_id()+"%");
        System.out.println(groups.size());
        SearchDataS searchDataS=new SearchDataS();
        List<SearchDataInfo>searchDataInfoList=new ArrayList<>();
        if(groups!=null){
            for(int i=0;i<groups.size();i++){
                SearchDataInfo searchDataInfo=new SearchDataInfo();
                Group group=groups.get(i);
                searchDataInfo.setGroup_uuid(group.getUuid());
                searchDataInfo.setGroup_desc(group.getHobby());
                searchDataInfo.setGroup_portarit(group.getPortarit());
                searchDataInfo.setGroup_name(group.getName());
                searchDataInfoList.add(searchDataInfo);
            }
            searchDataS.setOperateId(operateId);
            searchDataS.setDataInfoList(searchDataInfoList);
            searchDataS.setStatus("success");
            searchDataS.setInformation("获取群组成功");
            webSocket=new WebSocket(operateId,searchDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }else{
            searchDataS.setOperateId(operateId);
            searchDataS.setDataInfoList(searchDataInfoList);
            searchDataS.setStatus("fail");
            searchDataS.setInformation("获取群组失败");
            webSocket=new WebSocket(operateId,searchDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
        }
    }
    /*
        查看群成员和个人信息
     */
    public void searchGroupNumberInfo(WebSocket webSocket){
        System.out.println("查看群成员和个人信息");
        UserGroupNumberInfoC userGroupNumberInfoC=(UserGroupNumberInfoC) webSocket.getIMessage();
        int operateId=userGroupNumberInfoC.getOperateId();
        Session session=webSocket.getSession();
        User user=userRepository.findByUuid(userGroupNumberInfoC.getUuid());
        GroupNumberDataS groupNumberDataS=new GroupNumberDataS();
        GroupNumberInfo groupNumberInfo=new GroupNumberInfo();
        if(user==null){
            groupNumberDataS.setOperateId(operateId);
            groupNumberDataS.setStatus("fail");
            groupNumberDataS.setData(groupNumberInfo);
            webSocket=new WebSocket(operateId,groupNumberDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        groupNumberDataS.setOperateId(operateId);
        groupNumberDataS.setStatus("success");
        groupNumberInfo.setUserName(user.getName());
        groupNumberInfo.setUserPortarit(user.getPortrait());
        groupNumberInfo.setUserSign(user.getSign());
        groupNumberInfo.setUserPhone(user.getPhone());
        groupNumberDataS.setData(groupNumberInfo);
        webSocket=new WebSocket(operateId,groupNumberDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        webSocket.clear();
    }

    /*
        我
     */
    public void SearchForMe(WebSocket webSocket){
        System.out.println("我");
        UserForMeC userForMeC=(UserForMeC) webSocket.getIMessage();
        int operateId=userForMeC.getOperateId();
        Session session=webSocket.getSession();
        User me=userRepository.findByUuid(userForMeC.getUuid());
        MyDataS myDataS=new MyDataS();
        MyDataInfo myDataInfo=new MyDataInfo();
        if(me==null){
            myDataS.setOperateId(operateId);
            myDataS.setData(myDataInfo);
            myDataS.setStatus("fail");
            webSocket=new WebSocket(operateId,myDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        myDataS.setOperateId(operateId);
        myDataS.setStatus("success");
        myDataInfo.setUser_name(me.getName());
        myDataInfo.setUser_sign("个性签名"+me.getSign());
        myDataInfo.setUser_portrait(me.getPortrait());
        myDataS.setData(myDataInfo);
        webSocket=new WebSocket(operateId,myDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
    /*
        保存个人资料
     */
    public void savePersonalInfo(WebSocket webSocket){
        System.out.println("保存个人资料");
        UserPersonalInfoC userPersonalInfoC=(UserPersonalInfoC) webSocket.getIMessage();
        int operateId=userPersonalInfoC.getOperateId();
        Session session=webSocket.getSession();
        User user=userRepository.findByUuid(userPersonalInfoC.getUuid());
        SavePersonalDataS savePersonalDataS=new SavePersonalDataS();
        SavePersonalInfo savePersonalInfo=new SavePersonalInfo();
        if(user==null){
            savePersonalDataS.setOperateId(operateId);
            savePersonalDataS.setInformation("保存失败");
            savePersonalDataS.setData(savePersonalInfo);
            webSocket=new WebSocket(operateId,savePersonalDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        user.setPortrait(userPersonalInfoC.getUuid_pic());
        user.setName(userPersonalInfoC.getUser_name());
        user.setSign(userPersonalInfoC.getUser_qianming());
        user.setPhone(userPersonalInfoC.getUser_phone());
        user.setEmail(userPersonalInfoC.getUser_email());
        userRepository.saveAndFlush(user);
        savePersonalDataS.setOperateId(operateId);
        savePersonalDataS.setInformation("保存成功");
        savePersonalDataS.setData(savePersonalInfo);
        webSocket=new WebSocket(operateId,savePersonalDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        webSocket.clear();
    }
    /*
        用户注销
     */
    public void UserExit(WebSocket webSocket){
        System.out.println("用户注销");
        UserExitC userExitC=(UserExitC) webSocket.getIMessage();
        Session session=webSocket.getSession();
        int operateId=userExitC.getOperateId();
        OnlineUser onlineUser = onlineUserManager.getOnlineUserBySessionId(session.getId());
        ExitDataS exitDataS=new ExitDataS();
        ExitInfo exitInfo=new ExitInfo();
        if (onlineUser == null){
            onlineUser = onlineUserManager.getOnlineUserByUuid(userExitC.getUuid());
            if (onlineUser == null){
                exitDataS.setOperateId(operateId);
                exitDataS.setStatus("fail");
                exitDataS.setData(exitInfo);
                exitDataS.setInformation("不存在该用户，无需注销");
                webSocket=new WebSocket(operateId,exitDataS,null);
                try {
                    session.getBasicRemote().sendObject(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
                webSocket.clear();
                return;
            }
        }
        onlineUserManager.removeOnlineUser(onlineUser);
        /*
            更新群成员接受时间
            返回相关信息
         */
        List<Group>groups=groupUserRepository.getGroupsByUserUuid(userExitC.getUuid());

        if(groups==null){
            exitDataS.setOperateId(operateId);
            exitDataS.setStatus("fail");
            exitDataS.setData(exitInfo);
            exitDataS.setInformation("注销失败");
            webSocket=new WebSocket(operateId,exitDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        for(Group group:groups){
            User user=userRepository.findByUuid(userExitC.getUuid());
            if(user!=null){
                GroupUser groupUser=groupUserRepository.findByUserAndGroup(user,group);
                groupUser.setReceiveTime(new Date());
            }
        }
        exitDataS.setOperateId(operateId);
        exitDataS.setStatus("success");
        exitDataS.setData(exitInfo);
        exitDataS.setInformation("注销成功");
        webSocket=new WebSocket(operateId,exitDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        webSocket.clear();
    }

    /*
        解散群
     */
    public void dissolutionGroup(WebSocket webSocket){
        System.out.println("解散群");
        UserDissolutionGroupC userDissolutionGroupC=(UserDissolutionGroupC) webSocket.getIMessage();
        int operateId=userDissolutionGroupC.getOperateId();
        Session session=webSocket.getSession();
        //根据传入的群号和用户账号判断是有这个群号，并且是这个人创的
        Group group=groupRepository.getGroupByUuidAndCreater(userDissolutionGroupC.getGroup_id(),userDissolutionGroupC.getUuid());
        DissolutionDataS dissolutionDataS=new DissolutionDataS();
        DissolutionInfo dissolutionInfo=new DissolutionInfo();
        if(group==null){
            dissolutionDataS.setOperateId(operateId);
            dissolutionDataS.setStatus("fail");
            dissolutionDataS.setData(dissolutionInfo);
            dissolutionDataS.setInformation("不存在该群号，解散失败");
            webSocket=new WebSocket(operateId,dissolutionDataS,null);
            try {
                session.getBasicRemote().sendObject(webSocket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
            webSocket.clear();
            return;
        }
        //找出该群的所有群成员
        List<GroupUser> groupUsers=groupUserRepository.getAllGroupUserForGroupId(userDissolutionGroupC.getGroup_id());
        for(GroupUser groupUser:groupUsers){
            System.out.println(groupUser.getUser().getUuid());
            //删除该群的群成员
            groupUserRepository.delete(groupUser);
            if(!groupUser.getUser().getUuid().equals(userDissolutionGroupC.getUuid())){
                //从onlineUserManager管理器获取在线用户，如果用户在线，就发送消息通知给他
                OnlineUser onlineUser=onlineUserManager.getOnlineUserByUuid(groupUser.getUser().getUuid());
                if(onlineUser==null){
                    //如果用户不在线就往通知表里面插入一条数据
                    dissolutionGroup(userDissolutionGroupC,groupUser,group,0);
                }else{
                    //将解散消息通知到每一个在线的用户,除了自己，因为群主在下面已收到解散的通知了
                    DissolutionNotificationS notificationS=new DissolutionNotificationS();
                    DissolutionNotificationInfo notificationInfo=new DissolutionNotificationInfo();
                    notificationS.setOperateId(45);//随便给一个操作码
                    notificationS.setRole(1);//0表示群主，1表示群成员
                    notificationS.setStatus("success");
                    notificationInfo.setTime(new Date());
                    notificationInfo.setNoticeContent("你已被该账号用户\""+userDissolutionGroupC.getUuid()+"\""+"移出群");
                    notificationInfo.setUuid(userDissolutionGroupC.getUuid());
                    notificationS.setData(notificationInfo);
                    webSocket=new WebSocket(45,notificationS,null);
                    try {
                        onlineUser.getSession().getBasicRemote().sendObject(webSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }
                    webSocket.clear();
                    dissolutionGroup(userDissolutionGroupC,groupUser,group,1);
                }
            }
        }
        groupRepository.delete(group);//把这个群也删除
        System.out.println(group.getName());
        dissolutionDataS.setOperateId(operateId);
        dissolutionDataS.setStatus("success");
        dissolutionDataS.setData(dissolutionInfo);
        dissolutionDataS.setInformation("解散成功");
        webSocket=new WebSocket(operateId,dissolutionDataS,null);
        try {
            session.getBasicRemote().sendObject(webSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        webSocket.clear();
    }

    /*
        解散群的通知消息
     */
    public void dissolutionGroup(UserDissolutionGroupC dissolutionGroupC,GroupUser groupUser,Group group,int status){
        Notification notification =new Notification();
        notification.setContent("你已被该账号用户\""+dissolutionGroupC.getUuid()+"\""+"移出群");
        notification.setResult(3);//0审核加入群聊，1同意加入群聊，2拒绝加入群聊，3表示被踢出群
        notification.setStatus(status);//0表示该通知未读，1表示已读
        notification.setSendUserId(userRepository.findByUuid(dissolutionGroupC.getUuid()));
        notification.setReceiveUserId(userRepository.findByUuid(groupUser.getUser().getUuid()));
        notification.setGroupId(group);
        notification.setCreateTime(new Date());
        notificationRepository.save(notification);
    }
}
