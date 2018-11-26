(function(window){
	if(!window.ddzj)window.ddzj = {};
	var operateIdType = window.ddzj.OperateIdType;
	var evenDispatcher = window.ddzj.EventDispatcher;
	
	var GroupMember = function(){
		this.member = null;
		this.view = null;
		evenDispatcher.apply(this);
		
		this.init = function(member){
			this.member = member;
			this.createView();
		}

		this.createView = function(){
			var newView = document.createElement("li");			
			newView.innerHTML = '<img src="' + this.member.groupUserPortrait + '" />' + '<span>' + this.member.groupUserName  + '</span>' + '<input type="button" value="删除" class="deleteNumber"/>';;
			
			this.view = $(newView);
			this.addEvenListeners();
		}
		
		this.getView = function(){
			//添加事件监听
			return this.view;
			
		}
		
		
		this.addEvenListeners = function(){
			
			this.addOnDeleteClickListener(this,this.toDeleteClick);
		}

		this.addOnDeleteClickListener = function(obj,call){
			
			var callFunc = function(event){
				call.call(obj,event);
			}
			
			//添加点击事件
			$(this.view).children("input.deleteNumber").on("click",callFunc);
		}

		this.toDeleteClick = function(event){
			this.dispatchEventWith(operateIdType.DeleteGroupMember);
		}

	}
	window.ddzj.GroupMember = GroupMember;
})(window);
