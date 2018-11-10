(function(window){
	if(!window.ddzj)window.ddzj = {};
	var tool = window.ddzj.tool;
	var operateIdType = window.ddzj.OperateIdType;
	
	
	var WsAgent = function(){
		this.wsClinet = null;
		
		this.init = function(wsClinet){
			this.wsClinet= wsClinet;
			this.wsClinet.connect();
			
		}
		
		
		
		//用户登陆
		this.onLoginChat = function(userUuid,userPassword){
			
			var obj = new Object();
			obj.operateId = operateIdType.User_Login_C;
			obj.uuid = userUuid;
			obj.password = userPassword;
			
			this.sendMessage(obj);
		}
		
		//获取群资料
		this.onGetGroupData = function(groupId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Get_Group_Data_C;
			obj.groupId = groupId;
			
			var jsonData = tool.toJson(obj);
			this.sendMessage(obj);
		}
		
		//保存修改群资料
		this.onSaveGroupData = function(groupId, groupName, groupDec){
			var obj = new Object();
			obj.operateId = operateIdType.User_Save_GroupData_C;
			obj.group_id = groupId;
			obj.group_name = groupName;
			obj.group_dec = groupDec;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		this.sendMessage = function(obj){
			
			var jsondata = tool.toJson(obj);
			
			this.wsClinet.sendMessage(jsondata);
		}
		
		//用户推送消息
		this.onUserToSendMessage = function(userId,groupId,content){
			var obj = new Object();
			obj.operateId = operateIdType.User_Send_GroupMessage;
			obj.userUuid = userId;
			obj.groupUuid = groupId;
			obj.content = content;

			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//用户发送接收消息时间
		this.sendReceiveMessageTime = function(userId,groupId,timestamp){
			var obj = new Object();
			obj.operateId = operateIdType.User_Send_TimeStamp;
			obj.userUuid = userId;
			obj.groupUuid = groupId;
			obj.timeStamp = timestamp;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//用户创建群
		this.onSendCreateGroup = function(userId,groupName,groupHobby,groupDec){
			var obj = new Object();
			obj.operateId = operateIdType.User_CreateGroup_C;
			obj.uuid = userId;
			obj.groupName = groupName;
			obj.groupHobby = groupHobby;
			obj.groupDec = groupDec;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//查看群成员位置信息
		this.onGetGroupUserLocations = function(groupId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Group_Number_Location_C;
			obj.group_id = groupId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//电话联系群成员
		this.onPhoneToGroupUser = function(groupId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Phone_Relative_C;
			obj.group_id = groupId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
	
		
		//用户查找群
		this.onSendSearchGroups = function(searchGroupName){
			var obj = new Object();
			obj.operateId = operateIdType.User_Search_Group_Number_Info_C;
			obj.group_id = searchGroupName;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//用户发送加入群请求
		this.onUserEnterGroup = function(userId, groupId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Application_Enter_Group_C;
			obj.uuid = userId;
			obj.group_id = groupId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//处理通知
		this.onUserHandleNotification = function(sendId, receiveId, groupId, result, type){
			var obj = new Object();
			obj.request_user_uuid = receiveId;
			obj.send_user_uuid = sendId;
			obj.group_uuid = groupId;
			obj.result = result;
			
			if(type === 0){
				obj.operateId = operateIdType.User_Accept_Enter_Group_C;
			}else if(type === 1){
				obj.operateId = operateIdType.User_Refuse_Enter_Group_C;
			}
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//退出群聊
		this.onQuitGroup = function(groupId,userId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Quit_Group_C;
			obj.group_id = groupId;
			obj.user_uuid = userId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		
		
		//管理群成员
		this.onManagerGroupUser = function(groupId,userId,delId){
			var obj = new Object();
			obj.operateId = operateIdType.Manager_GroupUser;
			obj.group_id = groupId;
			obj.uuid = userId;
			obj.del_uuid = delId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//查看群成员个人信息
		this.onSearchGroupUserData =function(userId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Data_Info;
			obj.uuid = userId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//查看群成员个人信息
		this.onSearchOwn =function(userId){
			var obj = new Object();
			obj.operateId = operateIdType.User_For_Me_C;
			obj.uuid = userId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//我的资料
		this.onSearchOwnData =function(userId){
			var obj = new Object();
			obj.operateId = operateIdType.Search_Own_Data;
			obj.uuid = userId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//保存我的资料
		this.onSaveOwnData =function(userId, pic, name, qianming, phone, email){
			var obj = new Object();
			obj.operateId = operateIdType.Save_Own_Data;
			obj.uuid = userId;
			obj.uuid_pic = pic;
			obj.user_name = name;
			obj.user_qianming = qianming;
			obj.user_phone = phone;
			obj.user_email = email;

			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//用户注销
		this.onUserOffline =function(userId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Offline;
			obj.uuid = userId;


			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
		//解散群
		this.onDeleteGroup = function(userId, groupId){
			var obj = new Object();
			obj.operateId = operateIdType.User_Delete_Group_Number_C;
			obj.uuid = userId;
			obj.group_id = groupId;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
		
	}
	window.ddzj.WsAgent = WsAgent;
})(window);
