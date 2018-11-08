package com.group.zhtx.message;

import com.google.gson.Gson;
import com.group.zhtx.message.websocket.client.*;
import com.group.zhtx.util.common.WebSocketOperateUtil;

public class MessageFactory {

    private static Gson gson = new Gson();

    public static IMessage newMessageByOperateCode(int opercode, String data){

        switch (opercode){
            case WebSocketOperateUtil.User_Login_C:
                return gson.fromJson(data, UserLoginC.class);
            case WebSocketOperateUtil.User_CreateGroup_C:

                 return gson.fromJson(data, UserCreateGroupC.class);
            case WebSocketOperateUtil.User_Enter_Group_C:
                 return gson.fromJson(data,UserEnterGroupC.class);
            case WebSocketOperateUtil.User_Get_Group_Data_C:
                 return gson.fromJson(data, UserGetGroupDataC.class);
            case WebSocketOperateUtil.User_Quit_Group_C:
                return gson.fromJson(data,UserQuitGroupC.class);
            case WebSocketOperateUtil.User_Save_GroupData_C:
                return gson.fromJson(data, UserSaveGroupDataC.class);
            case WebSocketOperateUtil.User_Get_CallGroupUserPhone_C:
                return gson.fromJson(data, UserCallGroupUserByPhoneC.class);
            case WebSocketOperateUtil.User_Send_GroupMessage_C:
                return gson.fromJson(data,SendGroupMessageC.class);
            case WebSocketOperateUtil.User_Group_Number_Location_C:
                return gson.fromJson(data, UserLocationInfoC.class);
            case WebSocketOperateUtil.User_Phone_Relative_C:
                return gson.fromJson(data, UserRelativNumberC.class);
                /*
            case WebSocketOperateUtil.User_Application_Enter_Group_C:
                System.out.println("申请加入群聊"+opercode+"||"+WebSocketOperateUtil.User_Enter_Group_C);
                return gson.fromJson(data, UserApplicationEnterGroupC.class);
                */
            case WebSocketOperateUtil.User_Accept_Enter_Group_C:
                return gson.fromJson(data, UserAcceptAndRefuseEnterGroupC.class);
            case WebSocketOperateUtil.User_Refuse_Enter_Group_C:
                return gson.fromJson(data, UserAcceptAndRefuseEnterGroupC.class);
            case WebSocketOperateUtil.User_Delete_Group_Number_C:
                return gson.fromJson(data, UserDeleteGroupNumberC.class);
            case WebSocketOperateUtil.User_Search_Group_C:
                return gson.fromJson(data,UserSearchGroupC.class);
            case WebSocketOperateUtil.User_Search_Group_Number_Info_C:
                return gson.fromJson(data,UserGroupNumberInfoC.class);
            case WebSocketOperateUtil.User_For_Me_C:
                return gson.fromJson(data,UserForMeC.class);
            case WebSocketOperateUtil.User_Data_Info:
                return gson.fromJson(data,UserGroupNumberInfoC.class);
            case WebSocketOperateUtil.User_Save_Personal_Info:
                return gson.fromJson(data,UserPersonalInfoC.class);
            case WebSocketOperateUtil.User_Exit:
                return gson.fromJson(data,UserExitC.class);
            case WebSocketOperateUtil.User_Dissolution_Group:
                return gson.fromJson(data,UserDissolutionGroupC.class);
            default:
                return null;
        }

    }



}
