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
			
			this.sendMessage(obj);
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
		
		this.sendReceiveMessageTime = function(userId,groupId,timestamp){
			var obj = new Object();
			obj.operateId = operateIdType.User_Send_TimeStamp;
			obj.userUuid = userId;
			obj.groupUuid = groupId;
			obj.timeStamp = timestamp;
			
			var jsonData = tool.toJson(obj);
			this.wsClinet.sendMessage(jsonData);
		}
			
	}
	window.ddzj.WsAgent = WsAgent;
})(window);
