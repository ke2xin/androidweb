(function(window){
	if(!window.ddzj)window.ddzj = {};
	var operateIdType = window.ddzj.OperateIdType;
	var evenDispatcher = window.ddzj.EventDispatcher;
	
	var Notification = function(){
		this.Notification = null;
		this.view = null;
		evenDispatcher.apply(this);
		
		this.init = function(notification){
			this.Notification = notification;
			this.createView();
		}

		this.createView = function(){
			var newView = document.createElement("li");
			newView.innerHTML = '<img  src="img/p1.jpg"/>'+
								'<span class="applicationMessage">' + (this.Notification.content === undefined ? "" : this.Notification.content) + '</span>'+
								'<input type="button" value="同意" class="agree"/>' +
								'<input type="button" value="拒绝" class="refuse"/>';
			this.view = $(newView);
		}
		
		this.getView = function(){
			//添加事件监听
			this.addEvenListeners();
			return this.view;
			
		}
		
		
		this.addEvenListeners = function(){
			
			this.addOnReceiveClickListener(this,this.toReceiveClick);
			this.addOnRefuseClickListener(this,this.toRefuseClick);
		}

		this.addOnReceiveClickListener = function(obj,call){
			
			var callFunc = function(event){
				call.call(obj,event);
			}
			
			//添加点击事件
			$(this.view).children("input.agree").on("click",callFunc);
		}

		this.addOnRefuseClickListener = function(obj,call){
			
			var callFunc = function(event){
				call.call(obj,event);
			}
			
			//添加点击事件
			$(this.view).children("input.refuse").on("click",callFunc);
		}


		this.toReceiveClick = function(event){
			this.dispatchEventWith(operateIdType.Receive_Notification);
		}
		
		this.toRefuseClick = function(event){
			this.dispatchEventWith(operateIdType.Refuse_Notification);
		}
	}
	window.ddzj.Notification = Notification;
})(window);
