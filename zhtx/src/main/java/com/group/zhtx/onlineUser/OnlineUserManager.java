package com.group.zhtx.onlineUser;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineUserManager {


    /*
        key: 用户uuid
        value:onlieUser
     */
    public Map<String,OnlineUser> onlineUserMap = new HashMap<>();

    /*
        key: sessionId
        value:用户uuid
     */
    public Map<String,String> sessionMap = new HashMap<>();

    public void addOnlineUser(OnlineUser onlineUser){

    }

}
