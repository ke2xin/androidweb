//创建和初始化地图函数：

var map;
function initMap() { 
	createMap(); //创建地图
	setMapEvent(); //设置地图事件
	addMapControl(); //向地图添加控件
	addMapOverlay(); //向地图添加覆盖物
}

function createMap() {
	map = new BMap.Map("map");
	map.centerAndZoom(new BMap.Point(116.406749, 39.925292), 13);
}

function setMapEvent() {
	map.enableScrollWheelZoom();
	map.enableKeyboard();
	map.enableDragging();
	map.enableDoubleClickZoom()
}

function addClickHandler(target, window) {
	target.addEventListener("click", function() {
		target.openInfoWindow(window);
	});
}

function addMapOverlay() {}
//向地图添加控件
function addMapControl() {
	var scaleControl = new BMap.ScaleControl({
		anchor: BMAP_ANCHOR_BOTTOM_LEFT
	});
	scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
	map.addControl(scaleControl);
	var navControl = new BMap.NavigationControl({
		anchor: BMAP_ANCHOR_TOP_LEFT,
		type: BMAP_NAVIGATION_CONTROL_LARGE
	});
	map.addControl(navControl);
	var overviewControl = new BMap.OverviewMapControl({
		anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
		isOpen: true
	});
	map.addControl(overviewControl);
}



function createAndSearch(){
	var functionButton=document.getElementById('functionButton');
	var subMune=document.getElementById('subMune');
	var createAndSearch=document.getElementsByClassName('createAndSearch')[0];
	functionButton.onclick=function(){
		subMune.style.display='block';
		subMune.style.zIndex='4';
	}
	var lisbtn=createAndSearch.getElementsByTagName('li');
	for(var i=0;i<lisbtn.length;i++){
		lisbtn[i].onclick=function(){
			console.log(this);
			subMune.style.display='none';
			var name=this.getAttribute('name');
			console.log(createGroup);
			if(name=="createGroup"){
				createGroup.style.display='block';
				chatWindow.style.display='none';
				searchGroup.style.display='none';
				dataI.style.display='none';
			}else if(name=="searchGroup"){
				createGroup.style.display='none';
				chatWindow.style.display='none';
				searchGroup.style.display='block';
				dataI.style.display='none';
			}
		}
	}
}
function switchPanel(){
	var middleGroupChat=document.getElementById('middleGroupChat');
	var middleNotificationMessage=document.getElementById('middleNotificationMessage');
	var middleI=document.getElementById('middleI');
	var bottom=document.getElementById('bottom');
	var bottomMenu=bottom.getElementsByClassName('bottomMenu')[0];
	var lisBottomMenu=bottomMenu.getElementsByTagName('li');
	var title=document.getElementById('title');
	var groupChatImg=document.getElementById('groupChatImg');
	var notificationImg=document.getElementById('notificationImg');
	var IDataImg=document.getElementById('IDataImg');
	var groupChatSpan=document.getElementById('groupChatSpan');
	var notificationSpan=document.getElementById('notificationSpan');
	var IDataSpan=document.getElementById('IDataSpan');
	for(var i=0;i<lisBottomMenu.length;i++){
		lisBottomMenu[i].onclick=function(){
			var name=this.getAttribute('name');
			if(name=="groupChat"){
				middleGroupChat.style.display='block';
				middleNotificationMessage.style.display='none';
				middleI.style.display='none';
				title.innerHTML='群聊';
				groupChatImg.setAttribute('src','img/group_chat2.png');
				notificationImg.setAttribute('src','img/message_notification1.png');
				IDataImg.setAttribute('src','img/i_data1.png');
				groupChatSpan.style.color='dodgerblue';
				notificationSpan.style.color='white';
				IDataSpan.style.color='white';
			}else if(name=="notificaitonMessage"){
				middleGroupChat.style.display='none';
				middleNotificationMessage.style.display='block';
				middleI.style.display='none';
				title.innerHTML='消息';
				groupChatImg.setAttribute('src','img/group_chat1.png');
				notificationImg.setAttribute('src','img/message_notification2.png');
				IDataImg.setAttribute('src','img/i_data1.png');
				groupChatSpan.style.color='white';
				notificationSpan.style.color='dodgerblue';
				IDataSpan.style.color='white';
			}else if(name=="IData"){
				middleGroupChat.style.display='none';
				middleNotificationMessage.style.display='none';
				middleI.style.display='block';
				title.innerHTML='我';
				groupChatImg.setAttribute('src','img/group_chat1.png');
				notificationImg.setAttribute('src','img/message_notification1.png');
				IDataImg.setAttribute('src','img/i_data2.png');
				groupChatSpan.style.color='white';
				notificationSpan.style.color='white';
				IDataSpan.style.color='dodgerblue';
			}
		}
	}
}
function lastNumberAndMessage(){
	var groupChat=document.getElementById('groupChat');
	var lisgroupChat=groupChat.getElementsByTagName('li');
	
}
function searchMap(){
	var chatBoxTop=document.getElementById('chatBoxTop');
	var chatBoxTopPlus=document.getElementById('chatBoxTopPlus');
	var chatBoxListFuncMune=document.getElementById('chatBoxListFuncMune');
	var flag=true;
	chatBoxTopPlus.onclick=function(){
		if(flag){
			chatBoxTop.style.top='0px';
			chatBoxListFuncMune.style.display='block';
			flag=false;
		}else{
			chatBoxTop.style.top='50px';
			chatBoxListFuncMune.style.display='none';
			flag=true;
		}
	}
}
function addListener(){
	var chatWindowInfo=document.getElementById('chatWindowInfo');
	var chatWindowMap=document.getElementById('chatWindowMap');
	var chatBoxLocation=document.getElementById('chatBoxLocation');
	chatBoxLocation.onclick=function(){
		chatWindowInfo.style.display='none';
		chatWindowMap.style.display='block';
		var black=document.getElementById('black');
		console.log(black);
		black.onclick=function(){
			chatWindowInfo.style.display='block';
			chatWindowMap.style.display='none';
		}
	}
	var groupListFunc=document.getElementsByClassName('groupListFunc')[0];
	console.log(groupListFunc);
	var lis=groupListFunc.getElementsByTagName('li');
	for(var i=0;i<lis.length;i++){
		lis[i].onclick=function(){
			console.log(this);
		}
	}
	//点击群在右边显示
	var groupLis=groupChat.getElementsByTagName('li');
	var obj=null;
	for(var i=0;i<groupLis.length;i++){
		groupLis[i].onclick=function(){
			if(obj!=null){
				obj.style.background='none';
			}
			obj=this;
			createGroup.style.display='none';
			chatWindow.style.display='block';
			searchGroup.style.display='none';
			dataI.style.display='none';
			this.style.background='rgba(0,0,0,0.1)';
		}
	}
	var groupMangerFuncList=groupMangerFunc.getElementsByClassName('groupMangerFuncList')[0];
	var ullis=groupMangerFuncList.getElementsByTagName('li');
	for(var i=0;i<ullis.length;i++){
		ullis[i].onclick=function(){
			console.log(this);
			var name=this.getAttribute('name');
			if(name=="groupAnoun"){
				groupAnounPanel.style.display='block';
				groupNumberPanel.style.display='none';
				groupDataPanel.style.display='none';
				groupManager.style.display='none';
			}else if(name=="groupNumber"){
				groupAnounPanel.style.display='none';
				groupNumberPanel.style.display='block';
				groupDataPanel.style.display='none';
				groupManager.style.display='none';
			}else if(name=="groupData"){
				groupAnounPanel.style.display='none';
				groupNumberPanel.style.display='none';
				groupDataPanel.style.display='block';
				groupManager.style.display='none';
			}else if(name=='groupDissolution'){
				console.log(this);
				if(window.confirm("确认解散该群？")){
					alert("已解散该群！");
				}else{
					alert("已取消。");
				}
			}
		}
	}
	gapClose.onclick=function(){
		groupAnounPanel.style.display='none';
		groupNumberPanel.style.display='none';
		groupDataPanel.style.display='none';
		groupManager.style.display='block';
	}
	gnpClose.onclick=function(){
		groupAnounPanel.style.display='none';
		groupNumberPanel.style.display='none';
		groupDataPanel.style.display='none';
		groupManager.style.display='block';
	}
	gdpClose.onclick=function(){
		groupAnounPanel.style.display='none';
		groupNumberPanel.style.display='none';
		groupDataPanel.style.display='none';
		groupManager.style.display='block';
	}
	//切换聊天窗和功能 窗
	chatLIistButton.onclick=function(){
		console.log(this);
		chatBlack.style.display='block';
		chatLIistButton.style.display='none';
		chatGroupMessage.style.display='none';
		chatBox.style.display='none';
		chatGroupListFunc.style.display='block';
	}
	chatBlack.onclick=function(){
		console.log(this);
		chatBlack.style.display='none';
		chatLIistButton.style.display='block';
		chatGroupMessage.style.display='block';
		chatBox.style.display='block';
		chatGroupListFunc.style.display='none';
	}
	//公告和群管理切换
	var groupListFunc=chatGroupListFunc.getElementsByClassName('groupListFunc')[0];
	var groupListFuncLis=groupListFunc.getElementsByTagName('li');
	for(var i=0;i<groupListFuncLis.length;i++){
		groupListFuncLis[i].onclick=function(){
			console.log(this);
			var name=this.getAttribute('name');
			if(name=='anoun'){
				groupAnoun.style.display='block';
				groupManager.style.display='none';
				groupAnounPanel.style.display='none';
				groupNumberPanel.style.display='none';
				groupDataPanel.style.display='none';
			}else if(name=='manager'){
				groupAnoun.style.display='none';
				groupManager.style.display='block';
				groupAnounPanel.style.display='none';
				groupNumberPanel.style.display='none';
				groupDataPanel.style.display='none';
			}else if(name=='deleteMessage'){
				if(window.confirm("确认删除？")){
					alert("已经删除");
				}else{
					alert("已经取消");
				}
			}else if(name=='exitGroup'){
				if(window.confirm("退出群？")){
					alert("已经退出该群");
				}else{
					alert("已取消");
				}
			}
		}
	}
	//显示我的资料面板
	var Ilis=I.getElementsByTagName('li');
	for(var i=0;i<Ilis.length;i++){
		Ilis[i].onclick=function(){
			console.log(this);
			var name=this.getAttribute('name');
			if(name='myData'){
				createGroup.style.display='none';
				chatWindow.style.display='none';
				searchGroup.style.display='none';
				dataI.style.display='block';
			}
		}
	}
}

function addGlobalVeriable(){	//全局变量
	var createGroup=document.getElementById('createGroup');
	var chatWindow=document.getElementById('chatWindow');
	var searchGroup=document.getElementById('searchGroup');
	var dataI=document.getElementById('dataI');
	var groupChat=document.getElementById('groupChat');//群面板
	var groupMangerFunc=document.getElementById('groupMangerFunc');
	var groupAnounPanel=document.getElementById('groupAnounPanel');
	var groupNumberPanel=document.getElementById('groupNumberPanel');
	var groupDataPanel=document.getElementById('groupDataPanel');
	var groupManager=document.getElementById('groupManager');
	var gapClose=document.getElementById('gapClose');
	var gnpClose=document.getElementById('gnpClose');
	var gdpClose=document.getElementById('gdpClose');
	var chatLIistButton=document.getElementById('chatLIistButton');
	var chatBlack=document.getElementById('chatBlack');
	var chatGroupListFunc=document.getElementById('chatGroupListFunc');
	var groupAnoun=document.getElementById('groupAnoun');
	var groupManager=document.getElementById('groupManager');
	var chatGroupMessage=document.getElementById('chatGroupMessage');
	var chatBox=document.getElementById('chatBox');
	var chatGroupListFunc=document.getElementById('chatGroupListFunc');
	var I=document.getElementById('I');
}

function addLoadEvent(func){ 
	var oldonload=window.onload; 
	if(typeof window.onload!='function'){
		window.onload=func; 
	}else{
		window.onload=function(){
			oldonload();
			func();
		}
	}
}
addLoadEvent(createAndSearch);
addLoadEvent(switchPanel);
addLoadEvent(lastNumberAndMessage);
addLoadEvent(searchMap);
addLoadEvent(initMap);
addLoadEvent(addListener);
addLoadEvent(addGlobalVeriable);