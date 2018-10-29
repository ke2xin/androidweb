package com.group.zhtx.message;

import com.google.gson.Gson;
import com.group.zhtx.message.websocket.client.*;
import com.group.zhtx.message.websocket.service.getCallGroupUserByPhone.UserCallGroupUserByPhoneS;
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
            case WebSocketOperateUtil.User_Get_CallGroupUserPhone_C:
                return gson.fromJson(data, UserCallGroupUserByPhoneC.class);
            case WebSocketOperateUtil.User_Send_GroupMessage_C:
                return gson.fromJson(data,SendGroupMessageC.class);
            default:
                return null;
        }

    }



}
