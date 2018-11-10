(function(window){
	if(!window.ddzj)window.ddzj = {};
	var Event = function(operateId,data){
		
		/*
		 * 操作码
		 */
		this.operateId;
		/*
		 * 附带数据
		 */
		this.data = null;
		
		this.target = null;
		
		
		this.resect = function(operateId,data){
			this.operateId = operateId;
			this.data = data;
			return this;
		}
		
		this.resect(operateId,data);
	}
	
	window.ddzj.Event = Event;
	
	
	
	var EventPoor = function(){
		
		//事件池
		this.eventPoor = [];
		
		
		//出池
		this.getEventOnPoor = function(operateId,data){
			
			
			if(this.eventPoor.length){
				return this.eventPoor.pop().resect(operateId,data);

			}else{
				return new window.ddzj.Event(operateId,data);
			}
		}
		
		//入池
		this.toEventOnPoor = function(event){

			this.eventPoor[this.eventPoor.length] = event;
		}
	}
	window.ddzj.EventPoor = new EventPoor();
	
	
	
	
	var tool = window.ddzj.tool;
	var EventDispatcher = function(){
		//对应方法
		this.EventDispatchListener = [];
		//对应实体
		this.EventFunction = [];
		var eventPoor = window.ddzj.EventPoor;
		
		this.addEventDispatchListener = function(operateId,listener,func){
		
			var listenerc = this.EventDispatchListener[operateId];
			
			if(tool.isNull(listenerc)){
				this.EventDispatchListener[operateId] = [listener];
				
				var funcc = this.EventFunction[listener];
				if(tool.isNull(funcc)){
					this.EventFunction[listener] = func;
				}
			}else {
				var check = -1;
				
				for(var i=0; i < listenerc.length; i++){
					var l = listenerc[i];
					if(l === listener){
						check = 1;
						break
					}
				}
				
				if(check < 0){
					listenerc.push(listener);
					
					var funcc = EventFunction[listener];
					if(tool.isNull(funcc)){
						this.EventFunction[listener] = func;
					}
				}
			}
		}
		
		this.removeEventDispatchListener = function(operateId,listener){
			
			var listenerc = this.EventDispatchListener[operateId];
			
			if(tool.isNull(listenerc)){
				return;
			}
			
			//移除该方法
			for(var i = 0 ;i < listenerc.length; i++){
				
				if(listenerc[i] === listener){
					listenerc[i].splice(listener,1);
				}
			}
			
			
			var funcc = this.EventFunction[listener];
			
			if(tool.isNull(funcc)){
				return;
			}
			//移除对应方法实体
			this.EventFunction.splice(listener,1);
			
		}
		
		//移除某操作的所有监听
		this.removeEventDispatchListeners = function(operateId){
			if(operateId && this.EventDispatchListener){
				delete this.EventDispatchListener[operateId];
			}
		}
		
		this.removeAllEventDispatchListeners = function(){
			his.mEventListeners = null;
		}
		
		this.dispatchEvent = function(event){
			
			var operateId = event.operateId;

			var listeners = this.EventDispatchListener[operateId];
			
			if(tool.isNull(listeners)){
				return;
			}
			
			
			event.target = this;
			
			if(tool.isNull(listeners)){
				return;
			}
			
			if(listeners.length){
				
				for(var i = 0; i < listeners.length;++i){
					
					var listener = listeners[i];
					var numArgs = listener.length;
					var func = this.EventFunction[listener];
					if(numArgs === 0){
						listener.call(func);
					}else if(numArgs === 1){
						listener.call(func,event);
					}
				
				}	
			}
		}
		
		this.dispatchEventWith = function(operateId,data){
			//封装消息
			var Event = eventPoor.getEventOnPoor(operateId,data);

			//分发消息
			this.dispatchEvent(Event);
			
			//将消息入库，重复利用
			eventPoor.toEventOnPoor(Event);
		}
		
	}
	
	window.ddzj.EventDispatcher = EventDispatcher;
})(window);




