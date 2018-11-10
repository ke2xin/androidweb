(function(window){
	if(!window.ddzj)window.ddzj = {};
	var OperateIdType = function(){
		
		
		//用户登陆
	    this.User_Login_C = 1;
	
		//创建群
	    this.User_CreateGroup_C = 3;
	
		//进入群
	    this.User_Enter_Group_C = 3;
	
		//获取群资料
	    this.User_Get_Group_Data_C = 4;
	
		//退出群
	    this.User_Quit_Group_C = 5;
	
		//保存修改群资料
	    this.User_Save_GroupData_C = 6;
	
		//获取群定位信息
	    this.User_Group_Number_Location_C = 7;
	
		//获取用户电话
	    this.User_Phone_Relative_C = 8;
	
		//申请加入群聊
	    this.User_Application_Enter_Group_C = 9;
	
		//接收加入群聊
	    this.User_Accept_Enter_Group_C = 10;
	
		//拒绝加入群聊
	    this.User_Refuse_Enter_Group_C = 11;
	
		//删除群
	    this.User_Delete_Group_Number_C = 12;
	
		//查找群
	    this.User_Search_Group_C =13;
	
		//查找群信息
	    this.User_Search_Group_Number_Info_C = 14;
	
		//查看自己信息
	    this.User_For_Me_C = 15;
	
	
		//查看群成员和个人信息
	    this.User_Data_Info = 16;
	
	    this.User_Save_Personal_Info = 17;
    
    
    	this.User_Send_GroupMessage = 24;
    	//接收到新的群消息
    	this.User_Receive_GroupMessage = 23;
    	//打开聊天群
    	this.Open_ChatObj = 100;
    	//发送接收消息的时间截
    	this.User_Send_TimeStamp = 99;
	}
	window.ddzj.OperateIdType = new OperateIdType();
})(window);
