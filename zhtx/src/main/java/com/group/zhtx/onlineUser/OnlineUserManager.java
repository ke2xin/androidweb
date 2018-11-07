package com.group.zhtx.onlineUser;

import com.group.zhtx.model.Message;
import com.group.zhtx.repository.MessageRepository;
import com.group.zhtx.thread.AsyncThreadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserManager {
    /*
        key: 用户uuid
        value:onlieUser
     */
    public Map<String,OnlineUser> onlineUserMap;

    /*
        key: sessionId
        value:用户uuid
     */
    public Map<String,String> sessionMap;

    @PostConstruct
    public void initMethod(){
        onlineUserMap = new HashMap<>();
        sessionMap = new HashMap<>();
    }




    public void addOnlineUser(OnlineUser onlineUser){

        if (onlineUser == null)return;

        //根据在线用户UUID，添加在线用户
        String uuid = onlineUser.getData().getUuid();
        onlineUserMap.put(uuid,onlineUser);

        String sessionId = onlineUser.getSession().getId();
        sessionMap.put(sessionId,uuid);

        //添加进线程进行循环处理
        AsyncThreadManager.addCycle(onlineUser,1,1);
    }


    /*
        根据SessionId 获取onlineUser实体类
     */
    public OnlineUser getOnlineUserBySessionId(String sessionId){
        if (sessionId == null || sessionId.equals(""))return null;

        if(!sessionMap.containsKey(sessionId))return null;
        String uuid = sessionMap.get(sessionId);

        if(!onlineUserMap.containsKey(uuid))return null;

        return onlineUserMap.get(uuid);

    }

    /*
        根据用户UUID获取onlineUser实体
     */
    public OnlineUser getOnlineUserByUuid(String uuid){

        if (uuid == null || uuid.equals(""))return null;
        return onlineUserMap.get(uuid);
    }

    public boolean removeOnlineUser(OnlineUser onlineUser){
        if (onlineUser == null)return false;

        AsyncThreadManager.removeCycle(onlineUser,1,1);

        onlineUserMap.remove(onlineUser);

        sessionMap.remove(onlineUser.getSession().getId());
        return true;
    }
}
