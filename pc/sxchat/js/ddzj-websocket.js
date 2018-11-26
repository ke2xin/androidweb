(function(window){
	if(!window.ddzj)window.ddzj = {};
	
	var webSocketConfig = function(){
		this.OPERATEID = "operateId";
		
		this.CONNECT = "connected";
		
		this.CLOSE = "closed";
	}
	
	window.ddzj.webSocketConfig = new webSocketConfig();
	
})(window);

(function(window){
	if(!window.ddzj)window.ddzj = {};
	var webSocketConfig = window.ddzj.webSocketConfig;
	var eventDispatcher = window.ddzj.EventDispatcher;
	
	
	var WsClient = function(url){
		
		this.webSocket = null;
		this.url = url;
		this.isconnected = false;
		this.useText = true;
		//继承EventDispatcher
		eventDispatcher.apply(this);
		
		
		this.connect = function(){
			this.webSocket = new WebSocket(this.url);
			this.onOpenListener(this,this.onOpen);
			this.onMessageListener(this, this.onMessage);
			this.onCloseListener(this,this.onClose);
		}
		
		
		this.onOpenListener = function(ws,call){
			
			var callFunc = function(event){
				call.call(ws,event);
			}
			ws.webSocket.onopen = callFunc;
		}
		
		
		this.onMessageListener = function(ws,call){
			
			var callFunc = function(event){
				call.call(ws,event);
			}
			ws.webSocket.onmessage = callFunc;
		}
		

		this.onCloseListener = function(ws,call){
			var func = function(event){
				call.call(ws,event);
			}
			ws.webSocket.onclose = func;
		}
		
		//发送消息
		this.sendMessage = function(data){
			if(!this.isconnected){
				return;
			}
			
			if(this.useText){
				this.webSocket.send(data);
				return;
			}
			
			var blobs = new Blob([data]);
			this.webSocket.send(blobs);
		}
		
		
		this.onOpen = function(event){
			this.isconnected = true;
			//分发消息
			this.dispatchEventWith(webSocketConfig.CONNECT);
		}
		
		
		this.onMessage = function(event){
			var data = JSON.parse(event.data);
			var operateId = data.operateId;
			if(operateId === null || operateId === undefined){
				//没有操作码的直接无视
				return;
			}
			//分发消息
			this.dispatchEventWith(operateId,data);
		}
		
		
		this.onClose = function(e){
			this.isconnected = false;
			//分发消息
			console.log(e);
			this.dispatchEventWith(webSocketConfig.CLOSE);
		}
		
		
		this.onError = function(e){
			console.log("异常关闭");
			console.log(e);
		}
		
		
		//主动关闭
		this.toClose = function(){
			this.ws.close();
		}
		
	};
	
	
	window.ddzj.WsClient = WsClient;
})(window);


