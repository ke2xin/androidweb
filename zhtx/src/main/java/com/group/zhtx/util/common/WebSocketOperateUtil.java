package com.group.zhtx.util.common;

public class WebSocketOperateUtil {
    //图片的地址
    public final static String Portrait_Url = "http://172.17.144.79:8080/userPortrait/";
    //图片的后缀
    public final static String Portrait_Suffix = ".png";
    //默认用户的头像
    public final static String Portrait_Image = "http://172.17.144.79:8080/userPortrait/123456654321.png";
    //默认群头像
    public final static String Portrait_Image_Group ="http://172.17.144.79:8080/userPortrait/123321.png";
    //用户登录
    public final static int User_Login_C = 1;
    //用户首页
    public final static int User_Home_C = 2;
    //创建群
    public final static int User_CreateGroup_C = 3;
    //进入群聊
    public final static int User_Enter_Group_C = 4;
    //获取群资料
    public final static int User_Get_Group_Data_C = 5;
    //退出群聊
    public final static int User_Quit_Group_C = 6;

    public final static int User_Save_GroupData_C = 7;

    public final static int User_Group_Number_Location_C = 8;

    public final static int User_Phone_Relative_C = 9;

    public final static int User_Application_Enter_Group_C = 10;

    //接受加入群聊
    public final static int User_Accept_Enter_Group_C = 11;

    public final static int User_Refuse_Enter_Group_C = 12;

    public final static int User_Delete_Group_Number_C = 13;

    //搜索群
    public final static int User_Search_Group_C = 14;

    public final static int User_Search_Group_Number_Info_C = 15;

    public final static int User_For_Me_C = 155555;

    //查看我的资料
    public final static int User_Data_Info = 17;

    public final static int User_Save_Personal_Info = 18;

    //用户注销
    public final static int User_Exit = 19;

    //解散群
    public final static int User_Dissolution_Group = 20;

    public final static int User_Get_GroupUserLocation_C = 8;

    public final static int User_Get_CallGroupUserPhone_C = 9999;
    //消息推送
    public final static int Message_Push = 22;

    //发送通知
    public final static int Send_Notifications = 23;

    public final static int User_Send_GroupMessage_C = 21;


    //用户发送时间截
    public final static int User_Send_TimeStamp = 99;
    //保存用户位置信息
    public final static int User_Location_C = 24;

    //发布群公告
    public final static int User_Anoun = 25;
}
