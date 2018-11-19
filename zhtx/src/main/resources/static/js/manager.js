function globalVeriable(){
	var userManager=document.getElementById('userManager');
	var groupManager=document.getElementById('groupManager');
	var userManagerList=document.getElementById('userManagerList');
	var groupManagerList=document.getElementById('groupManagerList');
	var forbidUser=document.getElementById('forbidUser');
	var startUser=document.getElementById('startUser');
	var forbidGroup=document.getElementById('forbidGroup');
	var startGroup=document.getElementById('startGroup');
}
function addListener(){
	console.log(groupManagerList+"哈哈哈");
	var userFlag=false;
	var managerFlag=false;
	userManager.onclick=function(){
		if(!userFlag){
			userManagerList.style.display='block';
			userFlag=true;
		}else{
			userManagerList.style.display='none';
			userFlag=false;
		}
	}
	groupManager.onclick=function(){
		if(!managerFlag){
			groupManagerList.style.display='block';
			managerFlag=true;
		}else{
			groupManagerList.style.display='none';
			managerFlag=false;
		}
	}
	var userManagerListLis=userManagerList.getElementsByTagName('li');
	var groupManagerListLis=groupManagerList.getElementsByTagName('li');
	for(var i=0;i<userManagerListLis.length;i++){
		userManagerListLis[i].onclick=function(){
			var name=this.getAttribute('name');
			if(name=="forbidUser"){
				forbidUser.style.display='block';
				startUser.style.display='none';
				forbidGroup.style.display='none';
				startGroup.style.display='none';
			}else if(name=="startUser"){
				forbidUser.style.display='none';
				startUser.style.display='block';
				forbidGroup.style.display='none';
				startGroup.style.display='none';
			}
		}
	}
	for(var i=0;i<groupManagerListLis.length;i++){
		groupManagerListLis[i].onclick=function(){
			var name=this.getAttribute('name');
			if(name=="forbidGroup"){
				forbidUser.style.display='none';
				startUser.style.display='none';
				forbidGroup.style.display='block';
				startGroup.style.display='none';
			}else if(name=="startGroup"){
				forbidUser.style.display='none';
				startUser.style.display='none';
				forbidGroup.style.display='none';
				startGroup.style.display='block';
			}
		}
	}
}

function verifyLogin() {
    console.log("这是检查登录的：");
    var theUser=sessionStorage.getItem("theUser");
    console.log("这是检查登录的："+theUser);
    if(theUser==null){
    	window.location.href="http://localhost:8080/managerLogin";
	}
}

//执行异步请求函数
function asyncHandle(){
    console.log("行异步请求函数");
	var serch=new searchHandel(sendKeyWordToBack);
}
function searchHandel(handle) {
	console.log("这是异步函数的具体实现");
    var searchText=$("#searchText");
    console.log(searchText);
    var init=function () {//定义一个初始化函数
		console.log("这是初始化函数");
		searchText.bind("keyup",sendKeyWord);
    }
    var sendKeyWord=function (event) {
		var valText=$.trim(searchText.val());
		console.log("这是搜索的内容："+valText);
    }
}
function sendKeyWordToBack(keyword) {
	console.log("这是发送信息到后台");
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
addLoadEvent(globalVeriable);
addLoadEvent(addListener);
addLoadEvent(verifyLogin);
addLoadEvent(asyncHandle);