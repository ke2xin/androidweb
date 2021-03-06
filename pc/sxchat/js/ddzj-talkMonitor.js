(function(window){
	if(!window.ddzj)window.ddzj = {};
	var operateIdTpye = window.ddzj.OperateIdType;
	var wsConfig = window.ddzj.webSocketConfig;
	var tool = window.ddzj.tool;
	var wsAgent = window.ddzj.WsAgent;
	var wsClient = window.ddzj.WsClient;
	var Group = window.ddzj.GroupObj;
	var SearchGroup = window.ddzj.SearchGroup;
	var Notification = window.ddzj.Notification;
	var GroupMember = window.ddzj.GroupMember;
	var GroupLocationUser = window.ddzj.LocationGroupUser;
	var TalkMonitor = function(){
		//websocket对象
		this.webSocketClient = null;
		//聊天组
		this.groupMap = [];
		//现在的聊天组
		this.nowChatGroupObj = null;
		//自己的数据
		this.owner = null;
		this.currentFile = null;
		this.webSocketAgent = null;
		//时间截集合
		this.timeStamp = [];
		this.notificationMap = [];
		this.isConnect = false;
		this.searchGroup = [];
		this.groupMember = [];
		this.groupLocationUser = [];
		this.currentMap = null;
		this.init = function(url){
			this.disConnectWebsocket();
			
			this.webSocketClient = new wsClient(url);
			this.webSocketAgent = new wsAgent();
			this.webSocketAgent.init(this.webSocketClient);
			
			this.initWebSocketListener();
			this.initViewClickListener();
		}
		
		
		
		this.initWebSocketListener = function(){
			//登陆成功监听
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Login_C,this.onLoginSuccess,this); 
			//获取群资料监听
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Get_Group_Data_C,this.onGetGroupData,this); 
			//接收群消息
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Receive_GroupMessage,this.onReceiveGroupMessage,this);
			//接收返回的时间截验证
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Send_TimeStamp,this.getReceiveTime,this);
			//接收返回创建群的响应
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_CreateGroup_C,this.onMessageByCreateGroup,this);
			//接收返回搜索群的响应
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Search_Group_Number_Info_C,this.onMessageBySearchGroup,this);
			//用户加入群消息响应
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Application_Enter_Group_C,this.onMessageByEnterGroup,this);
			//用户接收到群通知
			this.webSocketClient.addEventDispatchListener(operateIdTpye.Receive_Notifications,this.onMessageByNotification,this);
			//接受通知响应
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Accept_Enter_Group_C,this.onMessageByReceiveNotification,this);
			//拒绝通知响应
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Refuse_Enter_Group_C,this.onMessageByRefuseNotification,this);
			//响应查看我的资料通知
			this.webSocketClient.addEventDispatchListener(operateIdTpye.Search_Own_Data,this.onMessageByLookOwnData,this);
			//响应保存我的资料
			this.webSocketClient.addEventDispatchListener(operateIdTpye.Save_Own_Data,this.onMessageBySaveOwnData,this);
			//响应群公告
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Send_GroupDec,this.onMessageBySendGroup,this);
			//响应查看群资料
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Get_Group_Data_C,this.onMessageByGetGroupData,this);
			//响应退出群
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Quit_Group_C,this.onMessageByonExitGroup,this);
			//响应保存群资料
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Save_GroupData_C,this.onMessageBySaveGroupData,this);
			//响应删除群成员
			this.webSocketClient.addEventDispatchListener(operateIdTpye.Manager_GroupUser,this.onMessageByDeleteMember,this);
			//响应获取群位置信息
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Group_Number_Location_C,this.onMessageByLocation,this);
			//解散群
			this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Delete_Group_Number_C,this.onMessageByDeleteGroup,this);
			
			this.webSocketClient.addEventDispatchListener(wsConfig.CONNECT,this.toConnect,this);
			this.webSocketClient.addEventDispatchListener(wsConfig.CLOSE,this.toClose,this);
		}
		
		this.initViewClickListener = function(){
			this.addSendClickListener(this,this.onSendGroupMessage);
			this.addSendCreateGroupMessageListener(this,this.onSendCreateGroupMessage);
			this.onKeySerachGroupsListener(this,this.onSendSearchGroups);
			this.onLookOwnDataClickListener(this, this.onLookOwnData);
			this.onSaveOwnDataClickListner(this,this.onSaveOwnData);
			this.onOfflineClickListener(this,this.onOffline);
			this.onSendGroupAnnnontionListener(this,this.onSendGroupAnnnontion);
			this.onLookGroupDataClickListener(this,this.onLookGroupData);
			this.onExitGroupClickListener(this,this.onExitGroup);
			this.onSaveGroupDataListener(this,this.onSaveGroupData);
			this.onLocationListener(this,this.onLocation);
			this.checkGroupRoleListener(this,this.checkGroupRole);
			this.onDeleteGroupClickListener(this,this.onDeleteGroup);
			$("body").on("click","#chatClose",function(){
				$("#chatWindow").attr("style","display: none;");
			});
			
			this.onChangeOwnImageClickListener(this,this.onChangeOwnImage);
			this.onChangeGroupImageClickListener(this,this.onChangeGroupImage);
		}
		
		this.onChangeOwnImageClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click",".changeImage",func);
		}
		
		this.onChangeGroupImageClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click",".groupReplaceImage",func);
		}
		
		this.onChangeOwnImage = function(){
			var fileInput = $(document.createElement("input"));
			this.currentFile = fileInput;
			fileInput.attr("type","file");
			fileInput.css("display","none");
			fileInput.click();
			fileInput.on("change",function(){
				var currentImage = this.files[0];
				var imgSizeBlock = 1024 * 1024 * 4;
				
				var imageType = /^image\//;
				
				if (!imageType.test(currentImage.type)) {
			        alert('请选择图片');
			        return;
			    }
				
				var unUsed = currentImage.size > imgSizeBlock;
				if(unUsed){
					alert("图片大于4M无法上传.");
					return;
				}
				
				var reader = new FileReader();
				reader.onload = function(e){
					$(".changeImage").attr("src",e.target.result);
				}
				
				reader.readAsDataURL(currentImage);
			});
		}

		
		this.onChangeGroupImage = function(){
			var fileInput = $(document.createElement("input"));
			this.currentFile = fileInput;
			fileInput.attr("type","file");
			fileInput.css("display","none");
			fileInput.click();
			fileInput.on("change",function(){
				var currentImage = this.files[0];
				var imgSizeBlock = 1024 * 1024 * 4;
				
				var imageType = /^image\//;
				
				if (!imageType.test(currentImage.type)) {
			        alert('请选择图片');
			        return;
			    }
				
				var unUsed = currentImage.size > imgSizeBlock;
				if(unUsed){
					alert("图片大于4M无法上传.");
					return;
				}
				
				var reader = new FileReader();
				reader.onload = function(e){
					$(".groupReplaceImage").attr("src",e.target.result);
				}
				
				reader.readAsDataURL(currentImage);
			});
		}
		
		
		//连接操作
		this.toConnect = function(event){
			var user = window.user;
			this.isConnect = true;
			this.webSocketAgent.onLoginChat(user.userName,user.password);
		}
		
		//服务器挂了啊
		this.toClose = function(event){
			alert("连接服务器失败了啊");
			this.webSocketClient = null;
			this.isConnect =false;
			//window.location = "login.html";
		}




		//登陆成功
		this.onLoginSuccess = function(event){
			var data = event.data.data;
			if(event.data.status == "fail"){
				alert("登陆失败");
				window.location = "login.html";
				return;
			}
			//登陆成功后倒计时去除
			setTimeout(function(){
				$(".forwardGround").remove();
			},1000);
			
			//存储自己的数据
			this.owner = data.singal;
			var groups = data.groups;
			$("#IPortrait").attr("src",this.owner.userPortrait);
			$(".iImage").attr("src",this.owner.userPortrait);
			$(".nickName").text(this.owner.userName);
			$(".sign").text(this.owner.userSign);
			//设置头像地址
			//$("#IPortrait").attr("src",this.owner.)
			//隐藏
			this.unView();
			
			if(groups.length){
				//设置首先打开的为第一个聊天组
				
				
				for(var i = 0; i < groups.length; ++i){
					var g = groups[i];
					var group = new Group();
					group.init(g);
					group.addEventDispatchListener(operateIdTpye.Open_ChatObj,this.openChatGroup,this);
					
					this.groupMap[g.groupNumber] = group;

					if(i === 0){
						this.nowChatGroupObj = group;
						this.nowChatGroupObj.getView().children("span.groupLastMessage").text("");
						this.nowChatGroupObj.getView().addClass("toPick");
					}
					
					$("#groupChat").append(group.getView());
				}
				//发送获取群资料请求
				this.webSocketAgent.onGetGroupData(this.nowChatGroupObj.group.groupNumber);
			}
			
		}
		
		//打开聊天群
		this.openChatGroup = function(event){
			var group = event.target.group;
			//如果打开的是当前的聊天群，直接无视
			
			if(this.nowChatGroupObj === null || this.nowChatGroupObj === undefined){
				this.onChangeChatGroup(event);
			}
			
			if(this.nowChatGroupObj.group.groupNumber === group.groupNumber){
				this.openView("chat");
				return;
			}
			//切换聊天群
			this.onChangeChatGroup(event);
			
		}
		
		this.onChangeChatGroup = function(event){
			this.nowChatGroupObj = event.target;
			$(".toPick").removeClass("toPick");
			this.nowChatGroupObj.getView().addClass("toPick");
			this.nowChatGroupObj.getView().children("span.groupLastMessage").text("");
			//发送获取群资料请求
			this.webSocketAgent.onGetGroupData(event.target.group.groupNumber);
		}
		
		
		//返回打开聊天群的数据
		this.onGetGroupData = function(event){
			var data = event.data;
			//如果返回的数据不是当前打开的聊天直接无视
			if(this.nowChatGroupObj.group.groupNumber !== data.groupNumber){
				return;
			}
			$(".chatGroupName").text(data.groupName);
			$(".chatGroupNotification").text(data.groupAnoun);
			$("#groupMessage").empty();
			this.onReviewMessage();
			this.openView("chat");
		}
		
		//恢复群聊天记录
		this.onReviewMessage = function(){
			var chatList = this.nowChatGroupObj.chatList;

			for(var i = 0; i < chatList.length ;i++){
				var chat = chatList[i];
				var li;
				//返回的消息发送的对象和发送的对象的ID相同则right
				if(chat.messageUserId == window.user.userName){
					li = this.getOtherMessageView("right", chat.messageUserName, chat.messageContent, chat.userPortrait);
				}else{
					li = this.getOtherMessageView("left", chat.messageUserName, chat.messageContent, chat.userPortrait);
				}
				$("#groupMessage").append(li);
				//滚动到底部
				$("#groupMessage").scrollTop(10000000000000000);	
			}
		}
		
		//发送已经接收消息的时间
		this.sendHaveReceiveMessageTime = function(){
			var dates = new Date();
			var times = dates.getTime();
			
		}

		//添加发送消息监听
		this.addSendClickListener = function(obj,call){
			
			var func = function(event){
				call.call(obj,event);
			}
			$("body").on("click","#chatBoxTopSend",func);
		}
		
		//用户主动发消息
		this.onSendGroupMessage = function(event){
			
			var content = $("#chatBoxContent").val();
			//空群发消息，无视
			if(!this.nowChatGroupObj)return;
			//没输入任何信息，无视
			if(content === "")return;
			if(this.webSocketClient === null)return;
			
			var groupId = this.nowChatGroupObj.group.groupNumber;
			var li = this.getOtherMessageView("right", this.owner.userName, content);
			
			//设置当前发送时间
			//var currentTime = this.onGetCurrentTime();
			//var timeli = this.onGetNotieView(currentTime);
			//$("#groupMessage").append(timeli);
			
			//$("#groupMessage").append(li);
			this.webSocketAgent.onUserToSendMessage(window.user.userName,groupId,content);
			$("#chatBoxContent").val("");
		}
		
		//添加创建群点击监听
		this.addSendCreateGroupMessageListener = function(obj,cal){
			
			var funCal = function(event){
				cal.call(obj,event);
			}
			$("body").on("click","#submitCreate",funCal);
		}
		
		//用户创建群
		this.onSendCreateGroupMessage = function(event){
			var inputs = $(".formCreateGroup").children().children().children().children().children("input");
			//输入参数不够四个，无视
			if(inputs.length < 4)return;
			
			var groupName = inputs.eq(0).val();
			var groupHobby = inputs.eq(1).val();
			var groupDec = inputs.eq(2).val();
			
			if(groupName === "" || groupHobby === "" || groupDec === "")alert("请确认你输入的内容正确！！！");
			if(inputs.eq(3).attr("id") != "submitCreate")return;
			
			inputs.eq(3).attr("disabled",true);
			
			this.webSocketAgent.onSendCreateGroup(window.user.userName, groupName, groupHobby, groupDec);
		}
			
		//发送查找群
		this.onSendSearchGroups = function(event){
			//$(searchBoxInput).val($(searchBoxInput).val().replace( /[^0-9]/g,''));
			var searchContent = $("#searchBoxInput").val();
          	
          	this.webSocketAgent.onSendSearchGroups(searchContent);
		}
		
		//返回创建群的消息响应
		this.onMessageByCreateGroup = function(event){
			var data = event.data;
			var groups = data.groups;
			var newGroup = groups[0];
			console.log(newGroup);
			if(newGroup === undefined)return;
			//添加群
			this.addGroupToMonitor(newGroup,"prepend");
			$(".formCreateGroup").children().children().children().children().children("input").eq(3).attr("disabled",false);

		}
		
		//创建新群
		this.addGroupToMonitor = function(g,type){
			var group = new Group();
			group.init(g);
			group.addEventDispatchListener(operateIdTpye.Open_ChatObj,this.openChatGroup,this);
			this.groupMap[g.groupNumber] = group;
			
			if(type === "prepend"){
				$("#groupChat").prepend(group.getView());
			}else if(type === "append"){
				$("#groupChat").append(group.getView());
			}
			group.getView().click();
			return group;
		}
		
		
		//添加搜索群按键监听
		this.onKeySerachGroupsListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}

			$("body").on("keyup","#searchBoxInput",func);
//			$("body").on("keyup","#searchBoxInput",function(){
//				    $(this).val($(this).val().replace( /[^0-9]/g,''));
//				});
		}
		
		//返回查找群的消息响应
		this.onMessageBySearchGroup = function(event){
			var data = event.data;
			var groups = data.data;
			console.log(data);
			if(groups){
				$(".searchCountInfo").empty();
				
				for(var i = 0; i < groups.length;i++){
					var group = groups[i];
					var searchGroup = new SearchGroup(group);
					if(!this.searchGroup){
						this.searchGroup =[];
					}
					//添加到搜索群记录
					if(this.searchGroup[group.groupUuid]){
						$(".searchCountInfo").append(this.searchGroup[group.groupUuid].getView());
					}else{
						this.searchGroup[group.groupUuid] = searchGroup;
						searchGroup.init(group);
						searchGroup.addEventDispatchListener(operateIdTpye.User_Application_Enter_Group_C,this.onEnterGroup,this);
						$(".searchCountInfo").append(searchGroup.getView());
					}
				}
				$(".searchCount").children("strong").text(groups.length);
			}
			
		}
		
		//用户加入群
		this.onEnterGroup = function(event){
			var target = event.target;
			var groupUuid = target.group.groupUuid;
			var userUuId = window.user.userName;
			if(!groupUuid || !userUuId)return;
			this.webSocketAgent.onUserEnterGroup(userUuId, groupUuid);
			$(target.getView()).children("input.searchApplication").attr("disabled",true);
			$(target.getView()).children("input.searchApplication").attr("value","申请中");
		}
		
		//返回加入群消息响应
		this.onMessageByEnterGroup = function(event){
			var data = event.data;
			var status = data.status;
			console.log(data);
			if(data === undefined || data == null || data === null)return;
			if(data.status == "fail"){
				if(this.searchGroup[data.groupId]){
					
					this.searchGroup[data.groupId].getView().children("input.searchApplication").attr("value","已加入");
				}
			}
			alert(data.information);
			
		}
		
		//接收到群通知
		this.onMessageByNotification = function(event){
			var datas = event.data.data;
			if(datas.length > 0){
				for(var i = 0; i < datas.length; ++i){
					var data = datas[i];
					var notif = new Notification();
					this.notificationMap[data.noticeId] = notif;
					notif.init(data);
					notif.addEventDispatchListener(operateIdTpye.Refuse_Notification, this.onRefuseNotification, this);
					notif.addEventDispatchListener(operateIdTpye.Receive_Notification, this.onReceiveNotification, this);
					$("#notificationMessage").prepend(notif.getView());
				}
			}
		}
		
		//接受通知
		this.onReceiveNotification = function(event){
			var target = event.target;
			var notif = target.Notification;
			var sendUserUuid = window.user.userName;
			var requsetUserUuid = notif.sendUuid;
			var groupUuid = notif.groupId;
			var noticeId = notif.noticeId;
			this.webSocketAgent.onUserHandleNotification(sendUserUuid, requsetUserUuid, groupUuid, "accept", noticeId, 0);
		}
		
		//拒绝通知
		this.onRefuseNotification = function(event){
			var target = event.target;
			var notif = target.Notification;
			var sendUserUuid = window.user.userName;
			var requsetUserUuid = notif.sendUuid;
			var groupUuid = notif.groupId;
			var noticeId = notif.noticeId;
			this.webSocketAgent.onUserHandleNotification(sendUserUuid, requsetUserUuid, groupUuid, "refuse", noticeId, 1);
		}

		//接受通知响应
		this.onMessageByReceiveNotification = function(event){
			var data =event.data;
			console.log(event.data);
			if(data.status == "accpet" || data.status == "success"){
				var infor = this.notificationMap[data.data.noticeId];
				if(infor){
					infor.getView().append('<span class="agree">' + '已同意加入' + '</span>');
					infor.getView().children("input").remove();
				}
			}
			
			//表示是用户被接受加入
			if(data.status == "accepted"){
				var groupObj = new Group();
				groupObj.init(data);
				console.log(groupObj);
				var view = groupObj.getView();
				this.groupMap[data.groupNumber] = groupObj;
				groupObj.addEventDispatchListener(operateIdTpye.Open_ChatObj,this.openChatGroup,this);
				$("#groupChat").prepend(view);
				
			}
		}
		
		//拒绝通知响应
		this.onMessageByRefuseNotification = function(event){
			var data = event.data;
			console.log(data);
			if(data.status == "success"){
				var infor = this.notificationMap[data.data.noticeId];
				if(infor){
					infor.getView().append('<span class="agree">' + '已拒绝加入' + '</span>');
					infor.getView().children("input").remove();
				}
			}
		}

		//查看我的资料点击监听
		this.onLookOwnDataClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click","#myData",func);
		}

		//查看我的资料
		this.onLookOwnData = function(event){
			var userId = window.user.userName;
			if(userId){
				this.webSocketAgent.onSearchOwnData(userId);
			}
			
		}
		
		//响应查看我的资料
		this.onMessageByLookOwnData = function(event){
			var data = event.data;
			var status = $.trim(data.status);
			//设置头像为空
			this.currentFile = null;
			
			if(status == "" || status === undefined || status === null || status == "fail"){
				alert("获取我的资料失败");
				
			}else if(status == "success"){
				var inputs = $("input.dataInfoDatile");
				if(inputs.length === 4){
					$(".changeImage").attr("src",data.data.userPortrait);
					inputs.eq(0).val(data.data.userName);
					inputs.eq(1).val(data.data.userSign);
					inputs.eq(2).val(data.data.userPhone);
					inputs.eq(3).val(data.data.userEmail);
					this.openView("dataI");	
					
				}
			}
			
		}
		
		//保存我的资料点击监听
		this.onSaveOwnDataClickListner = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click","#dataISave",func);
		}
		
		//保存我的资料
		this.onSaveOwnData = function(event){
			var inputs = $("input.dataInfoDatile");
			var name ="";
			var sign ="";
			var phone ="";
			var email ="";
			if(inputs.length === 4){
				name = $.trim(inputs.eq(0).val());
				sign = $.trim(inputs.eq(1).val());
				phone = $.trim(inputs.eq(2).val());
				email = $.trim(inputs.eq(3).val());
			}

			if (email == "") { 
				//$("#confirmMsg").html("<font color='red'>邮箱地址不能为空！</font>"); 
				alert("邮箱不能为空!") 
				return; 
			}else if(!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
				alert("邮箱格式不正确"); 
				return; 
			}
			
			if(phone == ""){
				alert("手机号码不能为空！"); 
				return;
			}else if(!phone.match(/^1[34578]\d{9}$/)){
				alert("手机号码格式不正确！"); 
				return;
			}
			
			if(name == ""){
				alert("名称不能为空!");
				return;
			}

			var userId = window.user.userName;
			
			var agent = this.webSocketAgent;
			if(this.currentFile === undefined || this.currentFile === null){
				agent.onSaveOwnData(userId,null,name,sign,phone,email);
				$("input#dataISave").attr("disabled",true);
				$("input#dataISave").attr("value","修改中...");
				return;
			}
			var filee = this.currentFile.context.files[0];
			if(filee === undefined || filee === null){
				agent.onSaveOwnData(userId,null,name,sign,phone,email);
				$("input#dataISave").attr("disabled",true);
				$("input#dataISave").attr("value","修改中...");
				return;
			}
			var reader = new FileReader();
			reader.readAsDataURL(filee);
			var binaryString;
			reader.onload=function(event){
				binaryString = event.target.result;
				
				agent.onSaveOwnData(userId,binaryString,name,sign,phone,email);
				$("input#dataISave").attr("disabled",true);
				$("input#dataISave").attr("value","修改中...");
			}
			
		}
		
		
		//响应保存我的资料
		this.onMessageBySaveOwnData = function(event){
			var status = event.data.status;
			console.log(event.data);
			if(status == "" || status === undefined || status === null || status == "fail"){
				alert("修改失败");
				
			}else if(status =="success"){
				alert("修改成功");
				var inputs = $("input.dataInfoDatile");
				var name ="";
				var sign ="";
				var phone ="";
				var email ="";
				if(inputs.length === 4){
					name = $.trim(inputs.eq(0).val());
					sign = $.trim(inputs.eq(1).val());
					$(".nickName").text(name);
					$(".sign").text(sign);
				}
				var srcContent = $(".changeImage").attr("src");
				$("#IPortrait").attr("src",srcContent);
				$(".iImage").attr("src",srcContent);
			}
			$("input#dataISave").attr("value","保存修改");
			$("input#dataISave").attr("disabled",false);
		}
		
		//用户点击退出登陆
		this.onOfflineClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			
			$("body").on("click",".exit",func);
		}
		
		//退出登陆
		this.onOffline = function(event){
			if(confirm("确认退出当前账号？")){
				console.log(!window.user);
				if(!window.user && !window.user.userName && !window.user.password)return;
				this.webSocketAgent.onUserOffline(window.user.userName);
				window.user = undefined;
				this.TalkMonitor = undefined;
				window.localStorage.clear();
				alert("退出登陆成功");
				window.location = "login.html";
			}
		}
		
		//响应退出登陆
		this.onMessageOffline = function(event){
			console.log(data);
		}
		
		//点击发送群公告
		this.onSendGroupAnnnontionListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}
			
			$("body").on("click","#publicAnoun",func);
		}
		
		//发送群公告
		this.onSendGroupAnnnontion = function(event){
			var group = this.nowChatGroupObj.group;
			var groupId = group.groupNumber;
			var groupName = group.groupName;
			var groupDec = $(".groupAnountion").val();
			console.log(groupDec);
			this.webSocketAgent.onSendGroupAnnoun(window.user.userName,groupId,groupDec);
			
			$("#publicAnoun").attr("value","发送中...");
			$("#publicAnoun").attr("disabled",true);
		}
		
		//响应群公告
		this.onMessageBySendGroup = function(event){
			console.log(event.data);
			$("#publicAnoun").attr("value","发布");
			if(event.data.status == "success"){
				var content = $(".groupAnountion").val();
				console.log(content);
				$(".chatGroupNotification").text(content);
				$("#publicAnoun").attr("disabled",false);
				alert("发布成功");
			}else{
				alert("发布失败");
			}
			
		}
		
		//查看群资料点击
		this.onLookGroupDataClickListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}
			
			$("body").on("click",".groupData",func);
			$("body").on("click",".manager",func);
			$("body").on("click",".groupNumber",func);
		}
		
		//查看群资料
		this.onLookGroupData = function(event){
			var groupId = this.nowChatGroupObj.group.groupNumber;
			if(groupId){
				this.webSocketAgent.onGetGroupData(groupId);
			}

		}
		
		//响应查看群资料
		this.onMessageByGetGroupData = function(event){		
			console.log(event.data);
			//群管理
			$("#groupTile").children("img").attr("src",event.data.groupPortrait);
			$(".groupManagerGroupName").text(event.data.groupName);
			$(".groupManagerGroupId").text(event.data.groupNumber);
			var members = event.data.members;
			if(members.length){
				this.groupMember = [];
				$(".groupNumberInfo").empty();
				$(".ControllerNumbersList").empty();
				for(var i = 0 ; i < members.length ; i++){
					var member = members[i];
					this.onNewGroupUserInformation(member.groupUserPortrait,member.groupUserName);
					
					var groupMember = new GroupMember();
					groupMember.init(member);
					var view = groupMember.getView();
					$(".ControllerNumbersList").append(view);
					//添加进
					this.groupMember[member.groupUserUuid] = groupMember;
					
					if(member.groupUserUuid == window.user.userName){
						view.children("input.deleteNumber").remove();
						continue;
					}
					groupMember.addEventDispatchListener(operateIdTpye.DeleteGroupMember,this.onDeleteGroupMember,this);
					
				}
				
				
			}
			
			//查看群资料
			$(".groupReplaceImage").attr("src",event.data.groupPortrait);
			$(".groupDataSaveName").attr("value",event.data.groupName);
			
			//管理群成员
			
		}
		
		//创建群资料成员名片
		this.onNewGroupUserInformation = function(groupUserPortrait,groupUserName){
			var newLi = document.createElement("li");														
			newLi.innerHTML = '<img src="' + groupUserPortrait + '" />' + '<span>' + groupUserName + '</span>';
			var newLi = $(newLi);
			newLi.addClass("groupNumberList");
			$(".groupNumberInfo").prepend(newLi);
			return newLi;
		}
		
		//删除用户点击监听
		this.onDeleteGroupMember = function(event){
			var target = event.target;
			var groupmember = target.member;
			//groupId,userId,delId
			var groupId = this.nowChatGroupObj.group.groupNumber;
			var userId = window.user.userName;
			var delId = target.member.groupUserUuid;
			if(!groupId || !userId || !delId){
				alert("生成用户错误");
			}
			this.webSocketAgent.onManagerGroupUser(groupId,userId,delId);
		}
		
		//响应删除用户
		this.onMessageByDeleteMember = function(event){
			var data = event.data;
			var userId = data.delUuid;
			console.log(event.data);
			console.log(this.groupMember);
			if(this.groupMember[userId]){
				alert("删除成功");
				var view = this.groupMember[userId].getView();
				//删除自己
				view.remove();
			}
		}
		
		//退出群点击事件
		this.onExitGroupClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click",".exitGroup",func);
		}
		
		//退出群
		this.onExitGroup = function(event){
			var userId = window.user.userName;
			var groupId = this.nowChatGroupObj.group.groupNumber;
			
			if(userId && groupId){
				this.webSocketAgent.onQuitGroup(groupId,userId);
			}
		}
		
		//响应退出群
		this.onMessageByonExitGroup = function(event){

			var data  = event.data;
			var groupId = data.groupId;
			if(groupId){
				var currentGroup = this.groupMap[groupId];
				if(currentGroup){
					var currentView = currentGroup.getView();
					currentView.remove();
					$("#chatClose").click();
					//移除元素
					var index = this.groupMap.indexOf(currentGroup);
					if(index){
						this.groupMap.splice(index,1);
					}
				}
			}
		}
		
		//保存群资料点击响应
		this.onSaveGroupDataListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click","#groupDataSure",func);
		}
		
		//定位信息监听
		this.onLocationListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}
			
			$("body").on("click","#chatBoxLocation",func);
			$("body").on("click","#black",function(){
				$("#chatWindowInfo").attr("style","display: block;");
				$("#chatWindowMap").attr("style","display: none;");
			});
			
		}
		
		//定位信息
		this.onLocation = function(event){
			var groupId = this.nowChatGroupObj.group.groupNumber;
			this.currentMap = new BMap.Map("map");
			this.currentMap.enableScrollWheelZoom();
			this.currentMap.enableKeyboard();
			this.currentMap.enableDragging();
			this.currentMap.enableDoubleClickZoom();
			var map = this.currentMap;
			var point = new BMap.Point(116.331398,39.897445);
			map.centerAndZoom(point,15);
			
			var talkMonitor = this;
			var owner = this.owner;
			var geolocation = new BMap.Geolocation();
			talkMonitor.openView("chatWindowMap");
			geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					var mk = new BMap.Marker(r.point);
		            map.addOverlay(mk);
		            map.panTo(r.point);
		            var opts = {
					  position : r.point,// 指定文本标注所在的地理位置
					  offset   : new BMap.Size(-5, 0) 
					}
					var label = new BMap.Label(owner.userName, opts);  // 创建文本标注对象
						label.setStyle({
								 color : "red",
								 fontSize : "1px",
								 height : "12px",
								 lineHeight : "12px",
								 fontFamily:"微软雅黑"
							});
					map.addOverlay(label);
		            
		            
					var newView = document.createElement("li");			
					newView.innerHTML = '<img src="' + owner.userPortrait + '">';
					$(".mapNumbersInfo").prepend(newView);
					
					ownerClick = function(){
						map.centerAndZoom(r.point,25);
					}
					
					$(newView).on("click",ownerClick);
					
					
				}
				else {
					alert('failed'+this.getStatus());
				}
			});

			
			
			if(groupId){
				this.webSocketAgent.onGetGroupUserLocations(groupId);
			}
		}
		
		
		//定位信息返回
		this.onMessageByLocation = function(event){
		console.log(event.data);
		//	this.openView("chatWindowMap");
			var data = event.data.data;
			//设置群组名
			$(".locationGroupName").text(this.nowChatGroupObj.group.groupName);
			//清空用户头像
			$(".mapNumbersInfo").empty();
			if(data.length){
				var currentMap = this.currentMap;
				var points;
				
				for(var i = 0; i < data.length; ++i){
					var currentData = data[i];
					var userName = currentData.userName;
					var userPortrait = currentData.userPortrait;
					var userLocationLatitude = currentData.userLocationLatitude;
					var userLocationLongitude = currentData.userLocationLongitude;

					if(points === undefined || points === null)points = [];
					if(userLocationLatitude == 4.9E-324 || userLocationLongitude == 4.9E-324)continue;
					
					//添加用户头像
					
					var locationUser = new GroupLocationUser();
					locationUser.init(currentData)
					$(".mapNumbersInfo").append(locationUser.getView());
					locationUser.addEventDispatchListener(operateIdTpye.ChangeLocationUser,this.onChangeLocationUser,this);
					var bitMapPoint = new BMap.Point(userLocationLongitude,userLocationLatitude);
		//			var bitMapPoint = new BMap.Point(113.037936,23.166192);
					points.push(bitMapPoint);
					
				}
				
				//坐标转换完之后的回调函数
				translateCallback = function(data){
					
					if(data.status === 0) {
				        for (var i = 0; i < data.points.length; i++) {
				            currentMap.addOverlay(new BMap.Marker(data.points[i]));
				            //currentMap.setCenter(data.points[i]);
				           
							
							var opts = {
							  position : data.points[i],// 指定文本标注所在的地理位置
							  offset   : new BMap.Size(-5, 0) 
							}
							var label = new BMap.Label(userName, opts);  // 创建文本标注对象
								label.setStyle({
										 color : "red",
										 fontSize : "1px",
										 height : "12px",
										 lineHeight : "12px",
										 fontFamily:"微软雅黑"
									});
							currentMap.addOverlay(label);
							//currentMap.setCenter(data.points[i]);
				        }
				    }
				}
				
				setTimeout(function(){
					var convertor = new BMap.Convertor();
        			convertor.translate(points, 5, 5, translateCallback);
				},1000);
				//打开地图窗口
			}
		}
		
		
		this.onChangeLocationUser = function(event){
			var locationMessage = event.target.locationMessage;
			var point = new BMap.Point(locationMessage.userLocationLongitude,locationMessage.userLocationLatitude);
			this.currentMap.centerAndZoom(point,30);
			//创建一个地理位置解析器  

            var geoc = new BMap.Geocoder();  
            geoc.getLocation(point, function(rs){//解析格式：城市，区县，街道  
                var addComp = rs.addressComponents;
                $("#mapInfoLocation").children("span").text("地址信息:    " + addComp.province + "," + addComp.city + ", " + addComp.district);
            });   
		}
		
		//保存群资料
		this.onSaveGroupData = function(event){
			//var groupImage = $(".groupDataSaveName").val();
			var groupName = $(".groupDataSaveName").val();
			var groupId = this.nowChatGroupObj.group.groupNumber;
			var groupAnoun = $(".chatGroupNotification").text();
			
			var reader = new FileReader();
			var filee = this.currentFile.context.files[0];
			reader.readAsDataURL(filee);
			var binaryString;
			var agent = this.webSocketAgent;
			reader.onload=function(event){
				binaryString = event.target.result;
				
				agent.onSaveGroupData(groupId,binaryString,groupName);
			}
		}
		
		//响应保存群资料
		this.onMessageBySaveGroupData = function(event)
		{
			var status = event.data.status;
			console.log(event.data);
			if(status == "success"){
				alert("修改群资料成功");
				var groupName = $(".groupDataSaveName").val();
				var srcContent = $('.groupReplaceImage').attr("src");
				this.nowChatGroupObj.group.groupPortrait = srcContent;
				$(".toPick").children("img").attr("src",srcContent);
				$(".chatGroupName").text(groupName);
				this.nowChatGroupObj.group.groupName = groupName;
				$(".toPick").children("span.groupName").text(groupName);
			}
		}
		
		//解散群点击  
		this.onDeleteGroupClickListener = function(obj,cal){
			var func = function(event){
				cal.call(obj,event);
			}
			$("body").on("click",".groupDissolution",func);
		}
		
		//解散群
		this.onDeleteGroup = function(event){
			var userId = window.user.userName;
			var groupId = this.nowChatGroupObj.group.groupNumber;
			
			this.webSocketAgent.onDeleteGroup(userId,groupId);
		}
		
		//解散群消息响应
		this.onMessageByDeleteGroup = function(event){
			var groupId = event.data.groupId;

			if(groupId){
				var groupObj = this.groupMap[groupId];
				if(groupObj){
					groupObj.getView().remove();
					var index = this.groupMap.indexOf(groupObj);
					if(index){
						this.groupMap.splice(index,1);
					}
					console.log(this.groupMap);
					$("#chatClose").click();
					alert("解散群成功");
				}
			}
		}
		
		//获取消息View
		this.getOtherMessageView = function(type,userName,userContent,userPortrait){
			var li = document.createElement("li");
			
			$(li).addClass(type);
			
			li.innerHTML = '<div class="chat-sender">'+
								'<div class="nickeNamePortrait"><img src="' + userPortrait + '" /></div>'+
								'<div class="nickName" >' + userName + '</div>'+
									'<div class="chatBackgroung">'+
										'<div class="chatMessageDatile">'+
											'<div class="chat-left_triangle"></div>'+
											'<span>' + userContent + '</span>'+
										'</div>'+
									'</div>'+
							'</div>';
			return li;
		}
		
		//用户被动接收消息
		this.onReceiveGroupMessage = function(event){
			var data = event.data.data;
			console.log(data);
			if(data.length){
				for(var i = 0; i< data.length; ++i){
					var currentData = data[i];
					var currentDataGroupNumber = currentData.groupUuid;
					var messages = currentData.messages;
					var timeStamp;
							
					//如果当前显示的组有消息发送过来，直接显示，没有就加入消息列表
					if(this.nowChatGroupObj.group.groupNumber == currentDataGroupNumber){
						for(var l = 0 ;l < messages.length; ++l){
							var m = messages[l];
							
							this.nowChatGroupObj.chatList.push(m);
							
							var li;
							//返回的消息发送的对象和发送的对象的ID相同则right
							if(m.messageUserId == window.user.userName){
								li = this.getOtherMessageView("right", m.messageUserName, m.messageContent, m.userPortrait);
							}else{
								li = this.getOtherMessageView("left", m.messageUserName, m.messageContent, m.userPortrait);
							}
							
							if(timeStamp === undefined){
								timeStamp = m.messageTime;
							}else if(timeStamp < m.messageTime){
								timeStamp = m.messageTime;
							}
							$("#groupMessage").append(li);
							//滚动到底部
							$("#groupMessage").scrollTop(10000000000000000);
						}
						this.timeStamp[currentDataGroupNumber] = timeStamp;
						this.sendReceiveTime(currentDataGroupNumber,timeStamp);
					}else{
						var groupObj = this.groupMap[currentDataGroupNumber];
						if(groupObj){
							var sender = messages[messages.length-1].messageUserName;
							var content = messages[messages.length-1].messagecontent;
							groupObj.getView().children("span.groupLastMessage").text(sender + ' : ' + content);
							
							for(var j = 0; j < messages.length; ++j){
								groupObj.chatList.push(messages[j]);

								if(timeStamp === undefined){
									timeStamp = messages[j].messageTime;
								}else if(timeStamp < m.messageTime){
									timeStamp = messages[j].messageTime;
								}
							}
							this.timeStamp[currentDataGroupNumber] = timeStamp;
							this.sendReceiveTime(currentDataGroupNumber,timeStamp);
						}
					}
				}
				//this.sendReceiveTime(timeStamps);
			}
		}
		
		//返回当前接收到群最新消息的时间
		this.sendReceiveTime = function(groupId,timestamp){
			var userId = window.user.userName;
			if(userId){
				this.webSocketAgent.sendReceiveMessageTime(userId,groupId,timestamp);
			}
		}
		
		//回去接收的时间验证
		this.getReceiveTime = function(event){
			console.log(event.data);
			var data = event.data;
			var groupId = data.groupId;
			
			if(this.timeStamp[groupId]){
				if(this.timeStamp[groupId] > data.timeStamp){
					this.timeStamp[groupId] = data.timeStamp;
				}
			}
		}
		
		//获取当前时间
		this.onGetCurrentTime = function(){
			var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var monthStr;
            var hour  = date.getHours();
            var second = date.getSeconds();
            var minutes = date.getMinutes();
            if (month < 10) {
                monthStr = "0" + month;
            } else {
                monthStr = month;
            }
            var dateStr;
            var date = date.getDate();
            if (date < 10) {
                dateStr = "0" + date;
            } else {
                dateStr = date;
            }
            if(minutes < 10){
            	minutes = "0" + minutes;
            }
            
            
            
            var time = year + "年" + monthStr + "月" + dateStr + "日" + hour +":" + minutes + ":" + second;
            return time;
		}
		
		//获取通知View
		this.onGetNotieView = function(content){
			var li = document.createElement("li");
			li.innerHTML = '<div class="chat-notice"><span class="notice">'+ content +'</span></div>';
			
			return $(li);
		}
		
		//隐藏所有布局
		this.unView = function(){
			$("#chatWindow").attr("style","display: none;");
			$("#createGroup").attr("style","display: none;");
			$("#searchGroup").attr("style","display: none;");
			$("#dataI").attr("style","display: none;");
			$("#chatWindowMap").attr("style","display: none;");
		}
		
		this.openView = function(type){
			this.unView();
			if(type === "chat"){
				$("#chatWindow").attr("style","display: block;");
			}else if(type === "createGroup"){
				$("#chatWindow").attr("style","display: none;");
			}else if(type === "searchGroup"){
				$("#chatWindow").attr("style","display: none;");
			}else if(type === "dataI"){
				$("#dataI").attr("style","display: block;");
			}else if(type === "chatWindowMap"){
				$("#chatWindow").attr("style","display: block;");
				$("#chatWindowInfo").attr("style","display: none;");
				$("#chatWindowMap").attr("style","display: block;");
			}
		}
		
		//下线操作
		this.disConnectWebsocket =function(){

			if(this.webSocketClient !== null && this.webSocketClient !== undefined){
				
				this.webSocketClient.toClose();
				this.webSocketClient.removeAllEventDispatchListeners();
				this.webSocketClient = null;
			}
		}
		
		this.checkGroupRoleListener = function(obj,cal){
			
			var func = function(){
				cal.call(obj);
			}
			
			$("body").on("click","#chatLIistButton",func);
		}
		
		this.checkGroupRole = function(){
			var role = this.nowChatGroupObj.group.groupRole;
			
			if(role == undefined || role == null)return;
			
			if(role == 0){
				$(".anoun").css("display","block");
				$(".groupNumber").css("display","block");
				$("#groupDataSure").css("display","inline-block");
				$(".exitGroup").css("display","none");
				$(".groupDissolution").css("display","block");
			}else{
				console.log(role);
				$(".anoun").css("display","none");
				$(".groupNumber").css("display","none");
				$("#groupDataSure").css("display","none");
				$(".exitGroup").css("display","block");
				$(".groupDissolution").css("display","none");
			}
		}
	}
	window.ddzj.TalkMonitor = TalkMonitor;
})(window);
