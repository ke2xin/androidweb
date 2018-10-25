package com.group.zhtx.message;

import com.google.gson.Gson;
import com.group.zhtx.message.websocket.client.UserCreateGroupC;
import com.group.zhtx.message.websocket.client.UserGetGroupDataC;
import com.group.zhtx.message.websocket.client.UserLoginC;
import com.group.zhtx.message.websocket.client.UserSaveGroupDataC;
import com.group.zhtx.util.common.WebSocketOperateUtil;

public class MessageFactory {

    private static Gson gson = new Gson();

    public static IMessage newMessageByOperateCode(int opercode, String data){

        switch (opercode){
            case WebSocketOperateUtil.User_Login_C:
                return gson.fromJson(data, UserLoginC.class);
             case WebSocketOperateUtil.User_CreateGroup_C:
                 return gson.fromJson(data, UserCreateGroupC.class);
             case WebSocketOperateUtil.User_Get_Group_Data_C:
                 return gson.fromJson(data, UserGetGroupDataC.class);
            case WebSocketOperateUtil.User_Save_GroupData_C:
                return gson.fromJson(data, UserSaveGroupDataC.class);
            default:
                return null;
        }

    }



}
