(function(window){
	if(!window.ddzj)window.ddzj = {};
	var operateIdTpye = window.ddzj.OperateIdType;
	var wsConfig = window.ddzj.webSocketConfig;
	var tool = window.ddzj.tool;
	var wsAgent = window.ddzj.WsAgent;
	var wsClient = window.ddzj.WsClient;
	var Group = window.ddzj.GroupObj;
	var TalkMonitor = function(){
		//websocket对象
		this.webSocketClient = null;
		//聊天组
		this.groupMap = [];
		//现在的聊天组
		this.nowChatGroupObj = null;
		//自己的数据
		this.owner = null;
		this.webSocketAgent = null;
		//时间截集合
		this.timeStamp = [];
		
		
		this.init = function(url){
			this.disConnectWebsocket();
			this.webSocketClient = new wsClient(url);
			this.webSocketAgent = new wsAgent();
			this.webSocketAgent.init(this.webSocketClient);
			
			this.webSocketClient.addEventDispatchListener(wsConfig.CONNECT,this.toConnect,this);
			this.webSocketClient.addEventDispatchListener(wsConfig.CLOSE,this.toClose,this);
			
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
			
			this.initViewClickListener();
			
			
		}
		
		
		this.initViewClickListener = function(){
			this.addSendClickListener(this,this.onSendGroupMessage);
			this.addSendCreateGroupMessageListener(this,this.onSendCreateGroupMessage);
			this.onKeySerachGroupsListener(this,this.onSendSearchGroups);
			
			$("body").on("click","#chatClose",function(){
				$("#chatWindow").attr("style","display: none;");
			})
		}
		
		//连接操作
		this.toConnect = function(event){
			var user = window.user;
			this.webSocketAgent.onLoginChat(user.userName,user.password);
		}
		
		//服务器挂了啊
		this.toClose = function(event){
			alert("连接服务器失败了啊");
			this.webSocketClient = null;
		}
		
		//登陆成功
		this.onLoginSuccess = function(event){
			var data = event.data.data;
			//存储自己的数据
			this.owner = data.singal;
			var groups = data.groups;
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
			if(this.nowChatGroupObj.group.groupNumber === group.groupNumber){
				this.openView("chat");
				return;
			}
			
			//切换聊天群
			this.nowChatGroupObj = event.target;

			$(".toPick").removeClass("toPick");
			this.nowChatGroupObj.getView().addClass("toPick");
			this.nowChatGroupObj.getView().children("span.groupLastMessage").text("");
			
			//恢复历史聊天记录
			
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
					li = this.getOtherMessageView("right", chat.messageUserName, chat.messagecontent);
				}else{
					li = this.getOtherMessageView("left", chat.messageUserName, chat.messagecontent);
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
			
		//发送请求群
		this.onSendSearchGroups = function(event){
			$(searchBoxInput).val($(searchBoxInput).val().replace( /[^0-9]/g,''));
			var searchContent = $("#searchBoxInput").val();
          	
          	this.webSocketAgent.onSendSearchGroups(searchContent);
		}
		
		//返回创建群的消息响应
		this.onMessageByCreateGroup = function(event){
			var data = event.data;
			var groups = data.groups;
			var newGroup = groups[0];
			
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
			
			return group;
		}
		
		
		//添加搜索群按键监听
		this.onKeySerachGroupsListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,event);
			}

			$("body").on("keyup","#searchBoxInput",func);
			$("body").on("keyup","#searchBoxInput",function(){
				    $(this).val($(this).val().replace( /[^0-9]/g,''));
				});
		}
		
		
		//返回查找群的消息响应
		this.onMessageBySearchGroup = function(event){
			console.log(event);
		}
		
		//获取消息View
		this.getOtherMessageView = function(type,userName,userContent){
			var li = document.createElement("li");
			
			$(li).addClass(type);
			
			li.innerHTML = '<div class="chat-sender">'+
								'<div class="nickeNamePortrait"><img src="img/p4.jpg" /></div>'+
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
								li = this.getOtherMessageView("right", m.messageUserName, m.messagecontent);
							}else{
								li = this.getOtherMessageView("left", m.messageUserName, m.messagecontent);
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
							
							console.log(messages[messages.length-1]);
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
			var data = event.data;
			if(this.this.timeStamp[groupId]){
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
		}
		
		this.openView = function(type){
			this.unView();
			if(type === "chat"){
				$("#chatWindow").attr("style","display: block;");
			}else if(type === "createGroup"){
				$("#chatWindow").attr("style","display: none;");
			}else if(type === "searchGroup"){
				$("#chatWindow").attr("style","display: none;");
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
		
		
	}
	window.ddzj.TalkMonitor = TalkMonitor;
})(window);
