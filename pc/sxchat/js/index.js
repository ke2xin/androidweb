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
	for(var i=0;i<groupLis.length;i++){
		groupLis[i].onclick=function(){
			createGroup.style.display='none';
			chatWindow.style.display='block';
			searchGroup.style.display='none';
			dataI.style.display='none';
		}
	}
}

function addGlobalVeriable(){	//全局变量
	var createGroup=document.getElementById('createGroup');
	var chatWindow=document.getElementById('chatWindow');
	var searchGroup=document.getElementById('searchGroup');
	var dataI=document.getElementById('dataI');
	console.log(createGroup);
	var groupChat=document.getElementById('groupChat');//群面板
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