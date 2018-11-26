(function(window){
	if(!window.ddzj)window.ddzj = {};
	var operateIdType = window.ddzj.OperateIdType;
	var evenDispatcher = window.ddzj.EventDispatcher;
	
	var LocationGroupUser = function(){
		this.locationMessage = null;
		this.view = null;
		evenDispatcher.apply(this);
		
		this.init = function(locationMessage){
			this.locationMessage = locationMessage;
			this.createView();
		}

		this.createView = function(){
			var newView = document.createElement("li");			
			newView.innerHTML = '<img src="' + this.locationMessage.userPortrait + '">';
			
			this.view = $(newView);
			this.addEvenListeners();
		}
		
		this.getView = function(){
			//添加事件监听
			return this.view;
			
		}

		this.addEvenListeners = function(){
			
			this.addChangeClickListener(this,this.toChangeClick);
		}

		this.addChangeClickListener = function(obj,call){
			
			var callFunc = function(event){
				call.call(obj,event);
			}
			
			//添加点击事件
			$(this.view).on("click",callFunc);
		}

		this.toChangeClick = function(event){
			this.dispatchEventWith(operateIdType.ChangeLocationUser);
		}
	}
	window.ddzj.LocationGroupUser = LocationGroupUser;
})(window);
