(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var OperateIdType = function () {



        //创建群
        this.User_CreateGroup_C = 3;

        //进入群
        this.User_Enter_Group_C = 3;

        //获取用户电话
        this.User_Phone_Relative_C = 8;

        //查找群
        this.User_Search_Group_C = 13;

        //查找群信息
        this.User_Search_Group_Number_Info_C = 14;

        //查看群成员和个人信息
        this.User_Data_Info = 16;

        this.User_Save_Personal_Info = 17;

        //发送群消息
        this.User_Send_GroupMessage = 21;
        //接收到新的群消息
        this.User_Receive_GroupMessage = 22;
        this.User_Send_GroupDec = 25;
        //用户登陆
        this.User_Login_C = 1;
        //获取群资料
        this.User_Get_Group_Data_C = 5;
        //退出群
        this.User_Quit_Group_C = 6;
        //保存修改群资料
        this.User_Save_GroupData_C = 7;
        //获取群定位信息
        this.User_Group_Number_Location_C = 8;
        //获取用户电话
        this.User_Phone_Relative_C = 9;
        //申请加入群聊
        this.User_Application_Enter_Group_C = 10;
        //接收加入群聊
        this.User_Accept_Enter_Group_C = 11;
        //拒绝加入群聊
        this.User_Refuse_Enter_Group_C = 12;
        //管理群成员
        this.Manager_GroupUser = 13;
        //查看群成员个人信息
        this.User_Data_Info = 15;
        //查看自己的信息
        this.User_For_Me_C = 16;
        //查看我的资料
        this.Search_Own_Data = 17;
        //保存我的资料
        this.Save_Own_Data = 18;
        //用户注销
        this.User_Offline = 19;
        //解散群
        this.User_Delete_Group_Number_C = 20;


        this.Receive_Notifications = 23;

        this.Refuse_Notification = 97;
        this.Receive_Notification = 96;
        //打开聊天群
        this.Open_ChatObj = 100;
        //剔除用户
        this.DeleteGroupMember = 96;

        //发送接收消息的时间截
        this.User_Send_TimeStamp = 99;


        //发送加入群
        this.User_Application_Enter_Group_C = 10;
    }
    window.ddzj.OperateIdType = new OperateIdType();
})(window);
