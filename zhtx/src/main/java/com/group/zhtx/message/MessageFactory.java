package com.group.zhtx.message;

import com.google.gson.Gson;
import com.group.zhtx.message.client.RegisterC;
import com.group.zhtx.util.common.WebSocketOperateUtil;

public class MessageFactory {

    private static Gson gson = new Gson();

    public static Message newMessageByOperateCode(int opercode,String data){

        switch (opercode){
            case WebSocketOperateUtil.User_Login_C:
                return gson.fromJson(data, RegisterC.class);
            default:
                return null;
        }

    }



}
