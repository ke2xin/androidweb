$(document).ready(function(){
	var TalkMonitor = window.ddzj.TalkMonitor;
	var operateIdTpye = window.ddzj.OperateIdType;
	var url = "ws://172.17.145.127:8080/ws";
	var User = function(userName,password){
		this.userName = userName;
		this.password = password;
	}
	
		this.init = function(){
			this.talkMonitor = new TalkMonitor();
			this.talkMonitor.init(url);
			this.talkMonitor.webSocketClient.addEventDispatchListener(operateIdTpye.User_Login_C,this.onMessageByLogin,this);
			this.onLoginClickListener(this,this.onLogin);
		}
		
		this.onLoginClickListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,cal);
			}
			$(".loginButton").on("click",func);
		}
		
		this.onLogin = function(event){
			var inputs = $("input.loginRowRight");
			if(inputs.length === 2){
				var userName = inputs.eq(0).val();
				var userPassword = inputs.eq(1).val();
				window.user = new User(userName,userPassword);

				if(this.talkMonitor.isConnect){
					this.talkMonitor.webSocketAgent.onLoginChat(window.user.userName,window.user.password);
					$(".loginButton").attr("disabled",true);
					$(".loginButton").attr("value","登陆中...");
				}else{
					alert("没有连接服务器");
				}
				
			}
		}
		
		this.onMessageByLogin = function(event){
			var data = event.data;
			if(data.status =="success"){
				$(".loginButton").attr("disabled",false);
				$(".loginButton").attr("value","登陆成功");
				this.onTalkMonitor();
			}else{
				$(".loginButton").attr("disabled",false);
				$(".loginButton").attr("value","登陆");
			}
		}
		
		this.onTalkMonitor = function(){
			$(".indexFrame").height($(".container").height());
			$(".indexFrame").width($(".container").width());
			$(".indexFrame").css("display","block");
		}
		
	window.onload = this.init();
});
